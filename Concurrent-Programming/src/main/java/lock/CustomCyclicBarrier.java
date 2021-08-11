package lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiang wenfa
 * @date 2021-07-14 20:30
 */
public class CustomCyclicBarrier{


    private int count;
    private final int parties;
    private final ReentrantLock lock;
    private final Condition waiter;

    public CustomCyclicBarrier(int count){
        if (count <= 0){
            throw new IllegalArgumentException("count <= 0");
        }
        this.count = count;
        this.parties = count;
        //MY-NOTE CyclicBarrier的功能就像是一个可以循环利用的CountDownLatch，但是实现上却完全不一样
        // CountDownLatch是用共享锁实现的，并且有一个内部类器继承AQS，但是CyclicBarrier底层是通过独占锁实现的
        // 在cb内部通过ReentrantLock去进行加锁：
        //      当count>0时：也就是计数器不为0，会将线程通过condition.await进行park，这些线程会进入到等待队列中，然后计数器count-1
        //      当count=0是：计数器为0，通过condition.signAll，将之前park的count个线程全部都唤醒，将他们放入同步队列中，等待获取锁
        // 假设count=3，t1和t2都会进入到等待队列，顺序是：t1 -> t2；
        // t3进入到doAwait方法后，满足count=0条件，会去signAll，t1和t2都会进入同步队列，顺序是t1 -> t2
        // 在t3释放锁之后，t1和t2在队列的头部，会依次去获取锁
        lock = new ReentrantLock();
        waiter = lock.newCondition();
    }

    private void reset(){
        count = parties;
        //MY-NOTE 重置计数器时，需要去唤醒全部被waiter条件阻塞的线程
        // 此时会将这些在等待队列中park住的线程全部都出队并且放到同步队列中，等待获取锁
        waiter.signalAll();
    }

    private void doAwait(){
        lock.lock();
        try {
            // 获取锁后，计数器减1
            int currCount = --count;
            // 如果currCount = 0，依旧是计数器倒数到0了，执行reset
            if (currCount == 0){
                reset();
            }else {
                waiter.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 发生异常也需要去重置计数器
            reset();
        } finally {
            lock.unlock();
        }
    }

    public void await() {
        doAwait();
    }

}
