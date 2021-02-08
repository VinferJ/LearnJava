package proxy.aop.game;

import proxy.aop.IGamePlay;

/**
 * @author Vinfer
 * @date 2021-01-25    13:41
 **/
public class GamePlay3 implements IGamePlay {

    @Override
    public void play(String param){
        System.out.println("play game3....param is["+param+"]");
    }


    @Override
    public String toString() {
        return super.toString();
    }

}
