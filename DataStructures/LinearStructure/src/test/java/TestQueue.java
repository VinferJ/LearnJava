import array.SparseArray;
import org.junit.Test;
import queue.ArrayQueue;
import queue.LinkedQueue;

/**
 * @author Vinfer
 * @date 2020-07-20  16:48
 **/
public class TestQueue {


    @Test
    public void testArrayQueue(){
        ArrayQueue<Integer> queue = new ArrayQueue<>(15);
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.peek());
        }
        System.out.println(queue.queueIsEmpty());
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.poll());
        }
        System.out.println(queue.queueIsEmpty());
        queue.add(996);
        queue.clear();
        System.out.println(queue.queueIsEmpty());
    }

}
