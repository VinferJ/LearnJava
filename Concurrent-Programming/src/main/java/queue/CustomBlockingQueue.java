package queue;

import java.util.concurrent.TimeUnit;

/**
 * @author vinfer
 * @date 2021-06-19 23:14
 */
public interface CustomBlockingQueue<E> {

    boolean offer(E e);

    boolean offer(E e,long timeout, TimeUnit timeUnit);

    void put(E e);

    E take();

    E poll(long timeout,TimeUnit timeUnit);

}
