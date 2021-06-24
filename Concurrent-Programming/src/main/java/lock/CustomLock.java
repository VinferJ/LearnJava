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

    private static final AtomicBoolean LOCK_FLAG = new AtomicBoolean(false);

    private static final Queue<Thread> SYNC_QUEUE = new ArrayDeque<>();

    private static final Queue<Thread> AWAIT_QUEUE = new ArrayDeque<>();

    public static class Condition{
        public void await(){
            while (!SYNC_QUEUE.contains(Thread.currentThread())) {
                if (!AWAIT_QUEUE.contains(Thread.currentThread())){
                    AWAIT_QUEUE.offer(Thread.currentThread());
                    // 当前线程需要释放锁
                    LOCK_FLAG.compareAndSet(true,false);
                }
                LockSupport.park();
            }
        }

        public void signal(){
            if (AWAIT_QUEUE.isEmpty()){
                return;
            }
            Thread signalThread = AWAIT_QUEUE.poll();
            SYNC_QUEUE.offer(signalThread);
            LockSupport.unpark(signalThread);
        }
    }

    public void lock(){
        for (;;){
            if (LOCK_FLAG.compareAndSet(false,true)) {
                return;
            }
            SYNC_QUEUE.offer(Thread.currentThread());
            LockSupport.park();
        }
    }

    public void lock(long nanos){
        for (;;){
            if (LOCK_FLAG.compareAndSet(false,true)) {
                return;
            }
            SYNC_QUEUE.offer(Thread.currentThread());
            LockSupport.parkNanos(nanos);
        }
    }

    public void unlock(){
        LockSupport.unpark(SYNC_QUEUE.poll());
        LOCK_FLAG.compareAndSet(true,false);
    }

    public Condition newCondition(){
        return new Condition();
    }

}
