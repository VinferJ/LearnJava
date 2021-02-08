package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    17:36
 * @description
 **/
public class GamePlayerProxyHandler implements InvocationHandler {

    /**要代理的对象的实例*/
    private Object proxySubject;

    public Object getProxyInstance(Object proxySubject){
        this.proxySubject = proxySubject;
        ClassLoader cl = this.proxySubject.getClass().getClassLoader();
        //为目标对象生成对应的代理对象
        return Proxy.newProxyInstance(cl,this.proxySubject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(proxy.aop.annotation.Proxy.class)){
            System.out.println("开始执行代理方法....");
            System.out.println("执行前处理....");
            if (args != null && args.length == 2){
                Object[] newArgs = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    newArgs[i] = args[i] + "_proxy";
                }
                Object result = method.invoke(proxySubject,newArgs);
                System.out.println("执行后处理...\n");
                return result;
            }else {
                Object res = method.invoke(proxySubject);
                System.out.println("执行后处理...\n");
                return res;
            }
        }else {
            int parameterCount = method.getParameterCount();
            if (parameterCount != 0){
                return method.invoke(proxySubject, args);
            }
            return method.invoke(proxySubject);
        }
    }
}
