package proxy.aop;

import proxy.aop.game.GamePlay3;
import proxy.aop.test.GamePlay;
import proxy.aop.test.GamePlay2;

/**
 * @author Vinfer
 * @date 2020-12-23    18:03
 **/
public class Test {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        IGamePlay gamePlay = proxyFactory.getProxyInstance(GamePlay.class);
        gamePlay.play("lol");
        System.out.println(gamePlay);
        IGamePlay gamePlay2 = proxyFactory.getProxyInstance(GamePlay2.class);
        gamePlay2.play("fate");
        System.out.println(gamePlay2);
        IGamePlay gamePlay3 = proxyFactory.getProxyInstance(GamePlay3.class);
        gamePlay3.play("毒奶粉");
        System.out.println(gamePlay3);
    }

}
