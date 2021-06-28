package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiang wenfa
 * @date 2021-06-24 20:07
 */
public class TestSynchronized {

    private static volatile int mutex = 0;

    private final Map<String,Objects> maps = new HashMap<>();

    private static ExecutorService executorService = new ThreadPoolExecutor(10,10,0L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {

            });
        }
    }

    public void simpleMethod(){
        // synchronized使用在局部代码块

        // 用类对象作为锁，那么线程竞争锁的时候修改的是该类对象的markWord
        synchronized (TestSynchronized.class){
            synchronized (TestSynchronized.class){
                System.out.println("thread:  "+Thread.currentThread().getName());
            }
        }

        // 使用对象实例作为锁，那么线程竞争锁的时候修改的是该实例对象的markWord
        synchronized (maps){

        }

        // this对象作为锁，跟对象实例作为锁是一个性质
        synchronized (this){

        }

    }

    public synchronized void instanceSyncMethod(){
        System.out.println(mutex);
    }

    public static synchronized void staticSyncMethod(){
        System.out.println(mutex);
    }

}
