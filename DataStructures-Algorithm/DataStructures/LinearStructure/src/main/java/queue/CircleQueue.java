package queue;

/**
 * @author Vinfer
 * @description         环形队列-基于数组实现，优点：可实现数组复用
 * 两种实现思路：
 *      1. rear指向最后一个元素     front=-1；rear=-1；有效数据个数：（rear+size-front）%size+1
 *
 *      2. rear指向最后一个元素的下一个位置，预留位置作为一个约定位（不存元素）  front=0；rear=0；有效数据个数：rear+size-front）%size
 *      使用第二种思路那么数组开辟空间就要要+1，本质上rear仍是数组最后一个元素的下标，但是该位置不存放数据，对于队列来说不是最后一个元素
 * @date 2020-07-23  09:12
 **/
public class CircleQueue<E> implements IQueue<E> {


    private static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE/10000;

    private final int size;

    private int frontPointer=-1;

    private int rearPointer=-1;

    private final Object[] elementData;

    public CircleQueue(int capacity) {
        size = capacity;
        elementData = new Object[size];
    }

    public CircleQueue(){
        this(DEFAULT_MAX_CAPACITY);
    }


    @Override
    public void add(E ele) {
        enqueue(ele);
    }

    void enqueue(E ele){
        checkNotNull(ele);
        if(isFull()){
            throw new RuntimeException("Queue is full");
        }else{
            /*
             * 环形队列入队，由于实现了数组复用，
             * 因此rear都要通过取模操作赋值
             * */
            rearPointer = (rearPointer+1)%size;
            if(frontPointer==-1){
                frontPointer++;
            }
            elementData[rearPointer] = ele;
        }
    }

    E dequeue(){
        if(queueIsEmpty()){
            throw new RuntimeException("Queue is empty");
        }else{
            E topEle = getTop();
            elementData[frontPointer] = null;
            /*
            * 如果此时头尾指针相等，说明最后一个元素以及出队
            * 此时要将队列头尾指针的值重置
            * */
            if(frontPointer == rearPointer){
                frontPointer = -1;
                rearPointer = -1;
            }else{
                /*
                * 对于front=size-1和front<size-1的两种情况
                * 直接通过取模操作就可以对两种情况下front都正确赋值（逻辑前移一位的操作）
                * */
                frontPointer = (frontPointer+1)%size;
            }
            return topEle;
        }
    }

    @SuppressWarnings("unchecked")
    E getTop(){
        return (E) elementData[frontPointer];
    }

    void checkNotNull(E ele){
        if(ele == null){
            throw new RuntimeException("Can't add a null element into queue");
        }
    }

    @Override
    public E peek() {
        if(queueIsEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        return getTop();
    }

    @Override
    public E poll() {
        return dequeue();
    }

    /**
     * 返回环形队列的有效数据个数
     * @return      环形队列的有效数据个数
     */
    @Override
    public int size() {
        return (rearPointer+size-frontPointer)%size+1;
    }

    @Override
    public boolean isFull() {
        //rearPointer-frontPointer==size-1||frontPointer-rearPointer==1
        return (rearPointer+1)%size == frontPointer;
    }

    @Override
    public boolean queueIsEmpty() {
        return frontPointer==-1&&rearPointer==-1;
    }

    @Override
    public void clear() {
        if(!queueIsEmpty()){
            for (int i = 0; i < size - 1; i++) {
                elementData[i] = null;
            }
            frontPointer = -1;
            rearPointer = -1;
        }
    }

    @Override
    public void display() {
        if(queueIsEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        //有效数据个数
        int realSize =size();
        int count = 0;
        /*遍历次数为front+有效数据个数*/
        for (int i = frontPointer; i < frontPointer+realSize; i++) {
            /*由于环形队列的原因，所以这里的i要取模size*/
            System.out.print(++count==realSize?elementData[i%size]:elementData[i%size]+" => ");
        }
    }
}
