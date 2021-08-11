package util;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vinfer
 * @date 2021-07-13 7:43
 */
public class CustomCountdownLatch {


    private volatile int count;

    private final ReentrantLock lock;

    private final Condition waiter;

    public CustomCountdownLatch(int count){
        if (count < 0){
            throw new IllegalArgumentException("count is less then zero!");
        }
        this.count = count;
        lock = new ReentrantLock();
        waiter = lock.newCondition();
    }

    public CustomCountdownLatch(){
        this(0);
    }

    public void countDown(){
        lock.lock();
        try {
            if (count == 0){
                waiter.signalAll();
            }else {
                AtomicInteger counter = new AtomicInteger(count);
                count = counter.decrementAndGet();
            }
        }finally {
            lock.unlock();
        }
    }

    public void await(){

    }

}
