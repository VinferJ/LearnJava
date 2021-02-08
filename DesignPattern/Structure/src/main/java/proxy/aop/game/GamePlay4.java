package proxy.aop.game;

import proxy.aop.IGamePlay;

/**
 * @author Vinfer
 * @date 2021-01-25    13:41
 **/
public class GamePlay4 implements IGamePlay {

    @Override
    public void play(String param){
        System.out.println("play game4....param is["+param+"]");
    }


    @Override
    public String toString() {
        return super.toString();
    }

}
