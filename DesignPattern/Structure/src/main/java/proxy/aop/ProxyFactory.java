package proxy.aop;


import com.sun.scenario.effect.impl.prism.PrImage;
import proxy.aop.annotation.ProxyHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Vinfer
 * @date 2021-01-25    10:14
 **/
public class ProxyFactory {

    private static final String SCANNING_PACKAGE = "proxy.aop";

    private final List<Class<?>> proxyHandlerList = new ArrayList<>();

    private final Map<String,Object> proxyClassMap = new HashMap<>(16);

    private final Map<String,Object> proxyInterfaceMap = new HashMap<>(16);

    private boolean isCurrentlyScanningProxyHandler;

    public ProxyFactory(){
        try {
            isCurrentlyScanningProxyHandler = true;
            scan(SCANNING_PACKAGE,this.proxyHandlerList);
            isCurrentlyScanningProxyHandler = false;
            doGenerate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scan(String scanningPath,List<Class<?>>storeList) throws IOException {
        ClassLoader cl = ProxyFactory.class.getClassLoader();
        Enumeration<URL> resources = cl.getResources(scanningPath.replace(".", "/"));
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            if (url != null){
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                this.doScan(cl,scanningPath,filePath,storeList);
            }
        }
    }

    private void doScan(ClassLoader cl,String packageName,String path,List<Class<?>> storeList){
        File file = new File(path);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                for (File scanningFile : files) {
                    if (scanningFile.isDirectory()){
                        String nextScanning = packageName + "." + scanningFile.getName();
                        String fileAbsolutePath = scanningFile.getAbsolutePath();
                        doScan(cl,nextScanning,fileAbsolutePath,storeList);
                    }else {
                        String fileName = scanningFile.getName();
                        String fullClassName = packageName + "." + fileName.substring(0, fileName.lastIndexOf('.'));
                        try {
                            Class<?> candidate = cl.loadClass(fullClassName);
                            if (isCurrentlyScanningProxyHandler){
                                if (candidate.isAnnotationPresent(ProxyHandler.class)){
                                    storeList.add(candidate);
                                }
                            }else {
                                storeList.add(candidate);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void doGenerate(){
        for (Class<?> clazz : proxyHandlerList) {
            ProxyHandler annotation = clazz.getAnnotation(ProxyHandler.class);
            String scanningPath = annotation.proxy();
            if (!scanningPath.isEmpty()){
                List<Class<?>> proxyClasses = new ArrayList<>();
                try {
                    //由@ProxyHandler配置的扫描路径下的所有类，使用同一个proxyHandler对象作为代理对象
                    GenericProxyHandler proxyHandler = new DelegateProxyHandler(clazz,null);
                    scan(scanningPath, proxyClasses);
                    for (Class<?> proxyClass : proxyClasses) {
                        Class<?>[] interfaces = proxyClass.getInterfaces();
                        if (interfaces.length == 0){
                            throw new RuntimeException("jdk dynamic proxy class requires implementing interface");
                        }
                        Class<?> proxyInterface = interfaces[0];
                        String proxyInterfaceName = proxyInterface.getName();

                        // TODO: 2021/1/27 如果类没有实现接口，使用cglib

                        //如果已经为该类的第一个接口创建了代理对象，并且对应的handler对象的代理类和当前类也是一致的，那么此时遍历一下个
                        if (proxyInterfaceMap.containsKey(proxyInterfaceName)){
                            Object proxy = proxyInterfaceMap.get(proxyInterfaceName);
                            GenericProxyHandler handler = (GenericProxyHandler) Proxy.getInvocationHandler(proxy);
                            if (handler.getProxyHandlerClass().isAssignableFrom(clazz)){
                                //为当前的代理目标缓存同一个代理对象，然后遍历下一个
                                proxyClassMap.put(generateCacheKey(proxyClass, proxyInterface), proxy);
                                continue;
                            }
                        }
                        Object proxySubject = proxyClass.newInstance();
                        ClassLoader cl = proxySubject.getClass().getClassLoader();
                        proxyHandler.setProxySubject(proxySubject);
                        /*
                        * jdk生成动态代理对象必须要使用接口（jdk的动态代理是基于接口的）
                        * 是因为在java中成为代理对象必须要继承Proxy对象，而java又是单继承，因此通过jdk生成动态代理对象只能基于接口
                        *
                        * 使用cglib可以基于类去生成该类的动态代理对象（cglib的动态代理是基于类的）
                        * 使用cglib生成代理对象，代理类本身还是会继承Proxy的，但是cglib会为代理类生成一个子类，子类会重写代理类的全部方法
                        * 因此cglib可以基于类去生成代理对象
                        *
                        * 在spring中的aop生成代理对象就有两种方式
                        * 当Bean实现了接口，那么会使用基于jdk的动态代理
                        * 如果Bean没有实现接口，那么会使用基于cglib的动态代理
                        *
                        * */
                        //基于接口为代理目标生成代理对象并缓存
                        Object proxyInstance = Proxy.newProxyInstance(cl, interfaces, proxyHandler);
                        proxyClassMap.put(generateCacheKey(proxyClass, proxyInterface) , proxyInstance);
                        if (!proxyInterfaceMap.containsKey(proxyInterfaceName)){
                            proxyInterfaceMap.put(proxyInterfaceName, proxyInstance);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxyInstance(Class<T> requiredType){
        System.out.println(proxyClassMap);
        System.out.println(proxyInterfaceMap);
        Class<?>[] interfaces = requiredType.getInterfaces();
        if (interfaces.length == 0){
            throw new RuntimeException("jdk dynamic proxy class requires implementing interface");
        }
        return (T)proxyClassMap.getOrDefault(generateCacheKey(requiredType, interfaces[0]), null);
    }

    private String generateCacheKey(Class<?> cl,Class<?> interfaceCl){
        return interfaceCl.getName() + "_" + cl.getName();
    }

}
