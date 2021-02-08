package proxy.aop.test;

import proxy.aop.IGamePlay;

/**
 * @author Vinfer
 * @date 2021-01-25    13:41
 **/
public class GamePlay implements IGamePlay {

    @Override
    public void play(String param){
        System.out.println("play game....param is["+param+"]");
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
