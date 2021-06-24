package lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vinfer
 * @date 2021-05-30 23:11
 */
public class TestAqs {

    static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        //MY-NOTE
        // synchronized关键字加锁，底层有JVM实现，通过1个monitor-enter和2个monitor-exit指令实现
        // synchronized在1.6版本做出了大量优化，具备锁膨胀升级（底层通过对象的MarkWord实现），锁粗化和锁消除（均为逃逸分析中JVM做的代码分析优化实现）以及自旋锁
        // 性能可以与Java实现的LOCK不相上下
        // synchronized用于代码块，锁的是括号内的对象，用于实例方法，锁的是该方法的实例对象，用于静态方法，锁的是静态方法所绑定的类对象
        // 逃逸分析优化：
        //  1.如果一个被创建的对象只会在一个线程栈中被引用（不会被其他线程栈所引用），即不会逃逸出被引用的一个线程，
        //    此时不会去创建该对象的实例，而是对这个实例进行标量替换（只创建对象中8大数据类型的属性到线程栈中进行使用）

        //MY-NOTE AQS
        // 阻塞等待队列
        // 共享/独占两种模式
        // 公平/非公平
        //  公平: 直接先去竞争一次获取锁，如果获取失败再进去唤醒队列中等待唤醒竞争获取锁
        //  非公平: 直接进入唤醒队列中等待唤醒竞争获取锁
        // 可重入
        // 允许中断
        // exclusiveOwnerThread

        //MY-NOTE AQS加解锁方式
        int taskCount = 100;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                taskCount,taskCount,0L, TimeUnit.NANOSECONDS,
                new ArrayBlockingQueue<>(taskCount),
                new ThreadPoolExecutor.AbortPolicy()
        );
        LockSupport.park();
        for (int i = 0; i < taskCount; i++) {
            executor.execute(TestAqs::useSync);
        }

    }

    static void useLock(){
        LOCK.lock();
        //MY-NOTE
        // lock加锁之后，只有一个线程可以进入下面的同步代码当中
        // 但是后面接着过来的99个线程怎么办？
        // 在AQS中获取锁失败的线程会通过LockSupport.park方法进行阻塞，并且会被添加到等待队列中，等待被唤醒
        //      while(true){
        //          if(加锁成功) break
        //          加锁失败:   等待 -> LockSupport.park -> 被唤醒后继续去竞争获取锁
        //      }
        try {
            // 任何时刻都必须保证，每次只能有一个线程进入到同步代码当前去执行
            // ======同步代码=======
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(100000);
            System.out.println("waked........");
            // =======同步代码=======
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            LOCK.unlock();
        }
    }

    static synchronized void useSync(){

        //MY-NOTE
        // LockSupport.park()让线程进入的是等待状态：WAITING
        // LockSupport.parkNanos(long)让线程进入的是超时等待状态：TIME_WAITING
        // synchronized加锁则是让线程进入阻塞状态：BLOCKED

        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(100000);
            System.out.println("waked........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
