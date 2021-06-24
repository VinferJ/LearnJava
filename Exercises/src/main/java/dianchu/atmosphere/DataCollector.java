package dianchu.atmosphere;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinfer
 * @date 2021-03-10 16:40
 */
public class DataCollector implements GenericNotifier{

    private final List<GenericBulletinBoard> bulletinBoards;
    private Object[] data;


    public DataCollector(){
        this.bulletinBoards = new ArrayList<>();
    }

    public static void main(String[] args) {
        int x=20,y=5;
        // 205255
        System.out.println(x+y+""+(x+y)+y);
        long i = 0xfffL;
        boolean a=false;
        boolean b=true;
        boolean c = b & a;
    }

    public void collectData(Object...data){
        this.data = data;
        // 当收集到新数据时，通知全体的订阅者
        notifyAllObserver();
    }


    @Override
    public void registerObserver(GenericBulletinBoard bulletinBoard) {
        this.bulletinBoards.add(bulletinBoard);
    }

    @Override

    public void removeObserver(GenericBulletinBoard bulletinBoard) {
        this.bulletinBoards.remove(bulletinBoard);
    }

    @Override
    public void notifyAllObserver() {
        for (GenericBulletinBoard bulletinBoard : this.bulletinBoards) {
            bulletinBoard.updateBulletinBoard(data);
        }
    }
}
