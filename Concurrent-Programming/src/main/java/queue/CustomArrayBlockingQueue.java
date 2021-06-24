package queue;

import lock.CustomLock;
import lock.MyLock;

import java.util.concurrent.TimeUnit;

/**
 * @author vinfer
 * @date 2021-06-19 23:17
 */
public class CustomArrayBlockingQueue<E> implements CustomBlockingQueue<E> {

    private final ArrayQueue<E> delegator;

    private final CustomLock readLock;

    private final CustomLock writeLock;

    private final CustomLock.Condition notFull;

    private final CustomLock.Condition notEmpty;

    public CustomArrayBlockingQueue(int size){
        delegator = new ArrayQueue<>(size);
        readLock = new CustomLock();
        writeLock = new CustomLock();
        notFull = writeLock.newCondition();
        notEmpty = readLock.newCondition();
    }

    private void enqueue(E e){
        if (!delegator.isFull()){
            delegator.add(e);
            notFull.signal();
        }
    }

    private E dequeue(){
        if (!delegator.queueIsEmpty()){
            notEmpty.signal();
            return delegator.poll();
        }else {
            return null;
        }
    }


    @Override
    public boolean offer(E e) {
        //System.out.println("locked by write lock");
        writeLock.unlock();
        try {
            delegator.add(e);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(timeout);
        writeLock.lock(nanos);
        try {
            delegator.add(e);
            return true;
        }catch (Exception ex){
            return false;
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public void put(E e) {
        writeLock.lock();
        try {
            while (delegator.isFull()){
                System.out.println(Thread.currentThread().getName() + ": await to put when queue is full....");
                notFull.await();
                System.out.println(Thread.currentThread().getName() + ": wake by signal....");
            }
            enqueue(e);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public E take() {
        //System.out.println("locked by read lock");
        readLock.lock();
        try {
            while (delegator.queueIsEmpty()){
                System.out.println(Thread.currentThread().getName() + ": await to take when queue is empty....");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + ": wake by signal....");
            }
            return dequeue();
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public E poll(long timeout, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(timeout);
        readLock.lock(nanos);
        try {
            return delegator.poll();
        }catch (Exception ex){
            return null;
        }finally {
            readLock.unlock();
        }
    }
}
