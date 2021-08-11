package queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiang wenfa
 * @date 2021-06-28 21:14
 */
public class CustomLinkedBlockingQueue<T>{

    private ArrayQueue<T> delegator;

    private final ReentrantLock readLock;

    private final ReentrantLock writeLock;

    private final Condition notFull;

    private final Condition notEmpty;

    private Node<T> head;

    private Node<T> tail;

    /** 容量不可修改 */
    private final int capacity;

    /** 当前队列元素数量 */
    private final AtomicInteger size;

    class Node<T>{
        T ele;
        Node<T> next;
        Node(T ele){
            this.ele = ele;
        }
    }

    public CustomLinkedBlockingQueue(){
        this(Integer.MAX_VALUE);
    }

    public CustomLinkedBlockingQueue(int capacity){
        this.capacity = capacity;
        size = new AtomicInteger(0);
        //MY-NOTE 读写不相互阻塞的前提，必须要操作的数据结构支持！！！！
        // ArrayBlockingQueue不支持读写不相互阻塞是因为底层数据结构是数组！！
        // 数组进行数据读写时，如果读写不相互阻塞，那么就有可能会出现有两个线程同时在操作一个下标的情况
        // 即有一个消费者线程读到了第n个元素，该元素准备要出队，操作的下标是n-1
        // 同时又有一个生成者线程写到第n个元素，该元素准备要入队，操作的下标也是n-1
        // 那么出现了上述的情况时，就是一种线程不安全的状况了，因此数组无法使用读写不阻塞，只能读写阻塞，
        // 保证同一时刻只有一个线程在操作同一个元素下标

        //MY-NOTE
        // LinkedBlockingQueue支持读写不相互阻塞，因为底层数据结构是链表，可以支持读写不阻塞
        // LinkedBlockingQueue在进行元素入队时，使用的是尾插，即在链表的尾结点新增一个next节点
        // 在进行元素出队时，是使头部元素进行出队，并且让head.next成为下一个head节点，这样可以达到先进先出的特性
        // 由于在元素出队和入队时，链表操作的都是不同的节点，所以可以支持读写不阻塞，
        // 在通过加锁操作后，就可以保证同时只有一个线程在操作同一个节点
        // LinkedBlockingQueue的头结点是不会存放元素值的，所以当队列中只有一个元素时，链表中会有两个节点对象
        // 因此一定可以满足上述元素出队和入队时，链表操作的都是不同的节点，

        //头结点不存放元素，因此初始化时要将头结点和尾结点初始化为一个空节点
        head = tail = new Node<>(null);



        // 读写不相互阻塞，那么就需要拥有两个不同的锁，读锁和写锁
        // 因为在AQS中维护的锁竞争的状态是一个成员变量，因此一个锁对象对应一个state，
        // 使用不同的锁进行同步，就可以达到不相互阻塞的目的
        readLock = new ReentrantLock();
        writeLock = new ReentrantLock();
        // 阻塞队列的put方法需要在队列满时进行非满即notFull的条件等待，在队列满时进行notFull.await，在队列不满时进行notFull.signal
        notFull = writeLock.newCondition();
        // 阻塞队列的take方法需要在队列空时进行非空即notEmpty的条件等待，在队列为空时进行notEmpty.await，在队列不空时进行notEmpty.signal
        notEmpty = readLock.newCondition();
        // 以上连个Condition对象在唤醒条件等待的线程时都必须要先获得对应的锁对象的锁
        // notFull.signal需要先获得writeLock的锁，也就是要在writeLock.lock之后进行唤醒
        // notEmpty.signal需要先获得readLock的锁，也就是要在readLock.lock之后进行唤醒
        // 为什么需要在对应的锁对象加锁之后才能去调用signal唤醒呢？
        //      1. 首先Condition对象的await和signal方法是对应的，必须是使用相同的条件进行等待和唤醒
        //      2. 需要加锁之后再去signal是因为await之后会自动释放线程对应的锁，然后park住当前线程，这样其他线程才能重新去竞争锁
        //         如果没加锁成功就意味着当前线程没有获得锁，因此需要确保await释放锁了，signal才有意义，因为signal操作会去unPark线程
        //         因此signal方法会去判断当前线程是不是已获得锁，满足时才会执行doSignal，这就是为什么一定要在lock之后才调用signal
        //
    }

    public void enqueue(T ele){
        // 入队时只操作队尾结点
        // 下面的操作等价于：
        //      tail.next = new Node<>(ele)
        //      tail = tail.next
        tail = tail.next = new Node<>(ele);
    }

    private T dequeue(){
        // 出队时只操作队列头结点，
        // 存放第一个元素的节点时链表中的第二个节点，也就是head.next
        // 当队列中有元素出队时，依旧要保持这样的结构，才能支持读写不阻塞的线程安全性
        Node<T> h = head;
        Node<T> firstEleNode = h.next;
        T dequeueEle = firstEleNode.ele;
        firstEleNode.ele = null;
        // 设置新的头结点
        head = firstEleNode;
        return dequeueEle;
    }

    private void signalNotEmpty(){
        // 唤醒被notEmpty条件等待的线程，由于notEmpty是readLock的Condition对象
        // 因此进行notEmpty.signal也需要先获得readLock的锁
        readLock.lock();
        try {
            notEmpty.signal();
        }finally {
            readLock.unlock();
        }
    }

    public boolean offer(T ele){
        writeLock.lock();
        try {
            if (size.get() == capacity){
                return false;
            }
            enqueue(ele);
            int currSize = size.incrementAndGet();
            if (currSize < capacity){
                // 如果当前队列未满，对notFull的条件等待线程进行唤醒
                // notFull是writeLock的Condition对象，因此必须要要在writeLock的锁中进行唤醒
                // 否则会抛出IllegalMonitorStateException的异常，因为当前独自的线程不是当前线程，即当前线程没有锁
                notFull.signal();
            }
        }finally {
            writeLock.unlock();
        }
        // 如果当前队列的size大于0，说明以及有元素入队，队列不为空
        // 需要去唤醒由notEmpty进行条件等待的线程
        if (size.get() > 0){
            signalNotEmpty();
        }
        return true;
    }

    public void put(T ele){
        writeLock.lock();
        try {
            // 要用while去阻塞线程，防止虚假唤醒
            while (size.get() == capacity){
                System.out.println(Thread.currentThread().getName() + " is awaited by not full condition");
                // 当前队列已满，需要等待notFull的条件唤醒，即notFull.signal
                // 当有元素未满时需要进行notFull.signal
                notFull.await();
                System.out.println(Thread.currentThread().getName() + " is signal by not full condition");
            }
            enqueue(ele);
            int currSize = size.incrementAndGet();
            if (currSize < capacity){
                notFull.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
        if (size.get() > 0){
            signalNotEmpty();
        }
    }

    private void signalNotFull(){
        writeLock.lock();
        try {
            // 进行notFull条件等待线程的唤醒，由于notFull是writeLock的Condition对象
            // 因此也需要先获得writeLock的锁才可以去唤醒由该Condition等待的线程
            notFull.signal();
        }finally {
            writeLock.unlock();
        }
    }

    public T poll(){
        readLock.lock();
        T ele;
        try {
            if (size.get() == 0){
                return null;
            }
            ele = dequeue();
            int currSize = size.decrementAndGet();
            if (currSize > 0){
                notEmpty.signal();
            }
        }finally {
            readLock.unlock();
        }
        // 如果当前队列还未满，需要去唤醒由notFull进行条件等待的线程
        if (size.get() < capacity){
            signalNotFull();
        }
        return ele;
    }

    public T take(){
        readLock.lock();
        T ele = null;
        try {
            // 这里必须要使用while循环来进行判断队列是否已满
            // 不可以用if来判断，因为用if可能会导致虚假唤醒
            // 因为if只有一次判断，如果线程只是当前满足条件进入了if中进行等待
            // 当被唤醒时会直接出去if，如果此时刚好有其他线程将队列又消费空了
            // 而当前线程再去消费时就会导致空指针异常
            // 如果在while循环中被唤醒，那么还是会再做新的判断，满足后队列非空后才会去消费
            while (size.get() == 0){
                System.out.println(Thread.currentThread().getName() + " is awaited by not empty condition");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + " is signal by not empty condition");
            }
            ele = dequeue();
            int currSize = size.decrementAndGet();
            if (currSize > 0){
                notEmpty.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
        if (size.get() < capacity){
            signalNotFull();
        }
        return ele;
    }

}
