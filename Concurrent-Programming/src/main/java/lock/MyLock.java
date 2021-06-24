package lock;

import sun.misc.Unsafe;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author vinfer
 * @date 2021-05-31 0:20
 */
public class MyLock {

    private final Queue<Thread> threadQueue = new LinkedBlockingQueue<>();

    private final AtomicBoolean locked = new AtomicBoolean(false);

    public void lock(){
        for (;;){
            if(locked.compareAndSet(false,true)){
                break;
            }else {
                threadQueue.offer(Thread.currentThread());
                LockSupport.park();
            }
        }
    }

    public void lock(long nanos){
        for (;;){
            if(locked.compareAndSet(false,true)){
                break;
            }else {
                threadQueue.offer(Thread.currentThread());
                LockSupport.parkNanos(nanos);
            }
        }
    }

    public void unlock(){
        if(locked.compareAndSet(true,false)){
            LockSupport.unpark(threadQueue.poll());
        }
    }

}
