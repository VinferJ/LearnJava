package proxy;

import proxy.aop.annotation.Proxy;

/**
 * @author Vinfer
 * @version plugin-v1.2
 * @date 2020-12-23    17:23
 **/
public interface GenericProxyPlayer {

    @Proxy
    Object play(String account,String password);

    void login();

    void logout();

}
