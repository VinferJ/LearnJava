package proxy;

/**
 * @author Vinfer
 * @date 2021-01-25    10:44
 **/
public class Player2 implements GenericProxyPlayer {
    @Override
    public Object play(String account, String password) {
        System.out.println("Player2 play...");
        return null;
    }

    @Override
    public void login() {
        System.out.println("Player2 login...");
    }

    @Override
    public void logout() {
        System.out.println("Player2 logout...");
    }
}
