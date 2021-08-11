package queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jiang wenfa
 * @date 2021-06-28 21:08
 */
public class Test {

    static final int w = 10000;

    public static void main(String[] args) {
       //testCustomQueue();
        // testJavaBlockingQueue();

    }

    static void testCustomQueue(){
        CustomLinkedBlockingQueue<Integer> bq = new CustomLinkedBlockingQueue<>();
        new Thread(() -> {
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10 * w; j++) {
                        System.out.println(Thread.currentThread().getName()+" producing: " + (j+1));
                        bq.put(j+1);
                    }
                },"producer-thread-"+(i+1)).start();
            }

        }).start();

        new Thread(() -> {
            for(;;){
                consume(bq);
            }
        },"consumer-thread-1").start();

        new Thread(() -> {
            for(;;){
                consume(bq);
            }

        },"consumer-thread-2").start();

        new Thread(() -> {
            for (;;){
                consume(bq);
            }
        },"consumer-thread-3").start();

    }

    static void testJavaBlockingQueue(){
        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>(10000 * 10);
        new Thread(() -> {
            for(;;){
                try {
                    consume(bq);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"consumer-thread-1").start();

        new Thread(() -> {
            for(;;){
                try {
                    consume(bq);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"consumer-thread-2").start();

        new Thread(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 999; i++) {
                System.out.println("producing: " + (i+1));
                try {
                    bq.put(i+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            new Thread(() -> {
                for (;;){
                    try {
                        consume(bq);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"consumer-thread-3").start();

        }).start();
    }

    static void consume(CustomLinkedBlockingQueue<Integer> bq) {
        Integer take = bq.take();
        System.out.println(Thread.currentThread().getName()+"-consuming: " + take);
    }

    static void consume(BlockingQueue<Integer> bq) throws InterruptedException {
        Integer take = bq.take();
        System.out.println(Thread.currentThread().getName()+"-consuming: " + take);
    }

}
