package queue;

import lock.CustomLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiang wenfa
 * @date 2021-06-28 21:14
 */
public class MyArrayBlockingQueue<T>{

    private final ArrayQueue<T> delegator;

    private final ReentrantLock readLock;

    private final ReentrantLock writeLock;

    private final Condition notFull;

    private final Condition notEmpty;

    public MyArrayBlockingQueue(){
        delegator = new ArrayQueue<>();
        readLock = new ReentrantLock();
        writeLock = new ReentrantLock();
        notFull = writeLock.newCondition();
        notEmpty = readLock.newCondition();
    }

    public void enqueue(T ele){
        delegator.add(ele);
        // 有元素入队了，那么意味着当前队列非空，需要去唤醒由notEmpty.await的线程
        notEmpty.signal();
    }

    public boolean offer(T ele){
        writeLock.lock();
        try {
            if (delegator.isFull()){
                return false;
            }
            enqueue(ele);
            return true;
        }finally {
            writeLock.unlock();
        }
    }

    public void put(T ele){
        writeLock.lock();
        try {
            // 这里必须要使用while循环来进行判断队列是否已满
            // 不可以用if来判断，因为用if可能会导致虚假唤醒
            // 因为if只有一次判断，如果线程被
            while (delegator.isFull()){
                // 当前队列已满，需要等待notFull的条件唤醒，即notFull.signal
                // 当有元素出队时需要进行notFull.signal
                notFull.await();
            }
            enqueue(ele);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public T poll(){
        return null;
    }

}
