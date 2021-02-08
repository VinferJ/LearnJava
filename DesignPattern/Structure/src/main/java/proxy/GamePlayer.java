package proxy;

import proxy.aop.annotation.Proxy;

/**
 * @author Vinfer
 * @version plugin-v1.2
 * @date 2020-12-23    17:02
 **/
public class GamePlayer implements GenericProxyPlayer{

    @Override
    public Object play(String account,String password){
        System.out.println("login with: [account:"+account+",pass:"+password+"]");
        System.out.println("play game....");
        return "hhh";
    }

    @Proxy
    @Override
    public void login() {
        System.out.println("login success...");
    }

    @Override
    public void logout() {
        System.out.println("logout success...");
    }

}
