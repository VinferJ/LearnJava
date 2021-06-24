package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author vinfer
 * @date 2021-06-19 23:34
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        testCustomQueue();
        //testJavaBlockingQueue();
    }

    static void testCustomQueue(){
        CustomBlockingQueue<Integer> bq = new CustomArrayBlockingQueue<>(10000 * 10);
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

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 9999; i++) {
                System.out.println("producing: " + (i+1));
                bq.offer(i+1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            new Thread(() -> {
                for (;;){
                    consume(bq);
                }
            },"consumer-thread-3").start();

        }).start();
    }

    static void consume(CustomBlockingQueue<Integer> bq){
        Integer take = bq.take();
        if (take != null){
            System.out.println(Thread.currentThread().getName()+"-consuming: " + take);
        }
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    static void testJavaBlockingQueue(){
        BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(10000 * 10);
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

            for (int i = 0; i < 9999; i++) {
                System.out.println("producing: " + (i+1));
                bq.offer(i+1);
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

    static void consume(BlockingQueue<Integer> bq) throws InterruptedException {
        Integer take = bq.take();
        System.out.println(Thread.currentThread().getName()+"-consuming: " + take);
    }



}
