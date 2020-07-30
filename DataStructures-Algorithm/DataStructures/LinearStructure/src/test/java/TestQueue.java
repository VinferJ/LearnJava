import org.junit.Test;
import queue.ArrayQueue;
import queue.CircleQueue;

/**
 * @author Vinfer
 * @date 2020-07-20  16:48
 **/
public class TestQueue {


    @Test
    public void testArrayQueue(){
        ArrayQueue<Integer> queue = new ArrayQueue<>(5);
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
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }
        queue.poll();
        queue.display();
        /*
        * 非环形队列，当队满之后，即使取出数据也无法在添加新数据
        * 因为要保持队列FIFO的特性，入队必须要尾指针后移
        * 而队满了意味着尾指针不能再后移了
        * */
        //queue.add(9);
    }

    @Test
    public void testCycleQueue(){
        CircleQueue<Integer> circleQueue = new CircleQueue<>(5);
        for (int i = 0; i < 5; i++) {
            circleQueue.add(i);
        }
        System.out.println(circleQueue.peek());
        circleQueue.poll();
        circleQueue.add(789);
        System.out.println(circleQueue.isFull());
        circleQueue.display();
        System.out.println("\n"+ circleQueue.queueIsEmpty());
    }


}
