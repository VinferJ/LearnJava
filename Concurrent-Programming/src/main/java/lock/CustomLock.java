package lock;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author vinfer
 * @date 2021-06-20 16:29
 */
public class CustomLock {

    private final AtomicBoolean lockFlag = new AtomicBoolean(false);

    private final Queue<Thread> syncQueue = new ArrayDeque<>();

    private final Queue<Thread> awaitQueue = new ArrayDeque<>();

    public class Condition{
        public void await(){
            while (!syncQueue.contains(Thread.currentThread())) {
                if (!awaitQueue.contains(Thread.currentThread())){
                    awaitQueue.offer(Thread.currentThread());
                    // 当前线程需要释放锁
                    lockFlag.compareAndSet(true,false);
                }
                LockSupport.park();
            }
        }

        public void signal(){
            if (awaitQueue.isEmpty()){
                return;
            }
            Thread signalThread = awaitQueue.poll();
            syncQueue.offer(signalThread);
            LockSupport.unpark(signalThread);
        }
    }

    public void lock(){
        for (;;){
            if (lockFlag.compareAndSet(false,true)) {
                return;
            }
            syncQueue.offer(Thread.currentThread());
            LockSupport.park();
        }
    }

    public void lock(long nanos){
        for (;;){
            if (lockFlag.compareAndSet(false,true)) {
                return;
            }
            syncQueue.offer(Thread.currentThread());
            LockSupport.parkNanos(nanos);
        }
    }

    public void unlock(){
        LockSupport.unpark(syncQueue.poll());
        lockFlag.compareAndSet(true,false);
    }

    public Condition newCondition(){
        return new Condition();
    }

}
