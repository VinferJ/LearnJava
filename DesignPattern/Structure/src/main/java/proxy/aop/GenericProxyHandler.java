package proxy.aop;

import java.lang.reflect.InvocationHandler;

/**
 * @author Vinfer
 * @date 2021-01-25    10:18
 **/
public interface GenericProxyHandler extends InvocationHandler{

    Object proxyEntrance(Object...args);

    void setProxySubject(Object proxySubject);

    Class<?> getProxyHandlerClass();

}
