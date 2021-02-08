package proxy.aop.test;

import proxy.aop.IGamePlay;


/**
 * @author Vinfer
 * @date 2021-01-27    09:39
 **/
public class GamePlay2 implements IGamePlay {

    @Override
    public void play(String param) {
        System.out.println("play game2....,param is ["+param+"]");
    }
}
