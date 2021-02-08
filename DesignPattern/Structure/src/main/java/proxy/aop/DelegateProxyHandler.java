package proxy.aop;

import proxy.aop.annotation.AfterRunning;
import proxy.aop.annotation.Around;
import proxy.aop.annotation.BeforeRunning;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Vinfer
 * @date 2021-01-25    10:59
 **/
public class DelegateProxyHandler implements GenericProxyHandler{


    private Method beforeRunningMethod;

    private Method afterRunningMethod;

    private Method aroundRunningMethod;

    private final Object proxyHandlerInstance;

    private final Class<?> proxyHandlerClass;

    private Object proxySubject;


    public DelegateProxyHandler(Class<?> proxyHandlerClass,Object proxySubject) throws IllegalAccessException, InstantiationException {
        this.proxySubject = proxySubject;
        this.proxyHandlerClass = proxyHandlerClass;
        this.proxyHandlerInstance = proxyHandlerClass.newInstance();
        //遍历代理对象的方法，扫描特殊注解标记的方法
        Method[] declaredMethods = proxyHandlerClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            Annotation[] annotations = declaredMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //根据不同的注解将得到的方法对象保存，目前只支持一对一
                if (annotationType.isAssignableFrom(BeforeRunning.class)){
                    beforeRunningMethod = declaredMethod;
                }
                if (annotationType.isAssignableFrom(Around.class)){
                    aroundRunningMethod = declaredMethod;
                }
                if (annotationType.isAssignableFrom(AfterRunning.class)){
                    afterRunningMethod = declaredMethod;
                }
            }
        }
    }

    @Override
    public void setProxySubject(Object proxySubject) {
        this.proxySubject = proxySubject;
    }

    @Override
    public Class<?> getProxyHandlerClass() {
        return this.proxyHandlerClass;
    }


    @Override
    public Object proxyEntrance(Object... args) {
        if (beforeRunningMethod != null){
            beforeInvoke();
        }
        return doInvoke(args);
    }

    private void beforeInvoke() {
        try {
            beforeRunningMethod.invoke(proxyHandlerInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object doInvoke(Object... args) {
        Object result = null;
        try {
            int parameterCount = aroundRunningMethod.getParameterCount();
            if (parameterCount > 0 && args != null){
                result = aroundRunningMethod.invoke(proxyHandlerInstance,args);
            }else {
                result = aroundRunningMethod.invoke(proxyHandlerInstance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void afterInvoke() {
        try {
            afterRunningMethod.invoke(proxyHandlerInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy invoke....");
        if (args == null){
            return method.invoke(proxySubject);
        }
        Object proxyResult = proxyEntrance(args);
        if (proxyResult != null){
            int parameterCount = method.getParameterCount();
            if (parameterCount > 0){
                args[0] = proxyResult;
                proxyResult = method.invoke(proxySubject, args);
            }else {
                proxyResult = method.invoke(proxySubject);
            }
        }
        if (afterRunningMethod != null){
            afterInvoke();
        }
        return proxyResult;
    }

}
