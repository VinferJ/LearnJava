package cas;

import java.util.Random;

/**
 * @author Vinfer
 * @date 2020-08-23    13:04
 **/
public class CasThread implements Runnable{

    /**操作同一个casInt对象，模仿并发访问*/
    private final CasOperations casOperations;
    private final String name;

    public CasThread(CasOperations casInt,String name){
        casOperations = casInt;
        this.name = name;
    }

    @Override
    public void run() {
        /*
        * 每次更新时，只有一个线程可以更新成功值，保证线程安全
        * */
        update();
        /*
        * 自增到100时，所有线程结束运行，即数值没有越界，线程安全
        * */
        //increment();
    }

    private void update(){
        while (true){
            int old = casOperations.getVal();
            int newVal = new Random().nextInt(10000);
            boolean flag = casOperations.compareAndSet(old, newVal);
            if(flag){
                System.out.println(name+" update val success "+"new val: "+newVal+" old val: "+old);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println(name+" update val fail");
            }
        }
    }

    private void increment(){
        while (casOperations.getVal()<=100){
            int curr = casOperations.casGetAndAdd();
            System.out.println(name+" add success: curr: "+curr);
        }
    }

}
