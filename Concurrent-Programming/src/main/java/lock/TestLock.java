package lock;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vinfer
 * @date 2021-05-31 0:28
 */
public class TestLock {

    static int count = 1000;
    static int sold = 0;
    static CyclicBarrier barrier = new CyclicBarrier(1000);

    public static void main(String[] args) throws InterruptedException {
        //test2();
        clean();
    }

    static void clean(String... list){
        Arrays.stream(list).filter(s -> !s.isEmpty()).forEach(System.out::println);
    }

    static void test1(){
        MyLock lock = new MyLock();
        ReentrantLock reLock = new ReentrantLock();
        int taskCount = 3000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                taskCount,taskCount,10L, TimeUnit.NANOSECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> {
                try {
                    barrier.await();
                    lock.lock();
                    if(count > 0){
                        count--;
                        sold++;
                        System.out.println("sold: "+sold);
                    }
                    //System.out.println("count: "+(++count));
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            });
        }
        ScheduledThreadPoolExecutor spc = new ScheduledThreadPoolExecutor(1);
        spc.schedule(() -> System.out.println("count: "+count+"    sold: "+sold),2000,TimeUnit.MILLISECONDS);
        spc.shutdown();
    }

    static void test2() throws InterruptedException {
        CustomLock lock = new CustomLock();
        CustomLock.Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("do await.......");
                condition.await();
                System.out.println("end await.......");
            }finally {
                lock.unlock();
            }

        },"wait-thread").start();

        Thread.sleep(2000);

        new Thread(() -> {
            //lock.lock();
            try {
                System.out.println("do singal.........");
                condition.signal();
            }finally {
                //lock.unlock();
            }
        },"signal-thread").start();
    }



}
