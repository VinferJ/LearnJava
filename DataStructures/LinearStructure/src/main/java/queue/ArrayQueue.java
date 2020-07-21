package queue;

/**
 * @author Vinfer
 * @description     队列-基于数组实现
 * @date 2020-07-20  16:35
 **/
public class ArrayQueue<E> implements IQueue<E>{

    private static final int DEFAULT_MAX_CAPACITY=Integer.MAX_VALUE;

    private int size;

    private int frontPointer=-1;

    private int rearPointer=-1;

    private final Object[] elementData;


    public ArrayQueue(int capacity){
        size=capacity;
        elementData=new Object[size];

    }

    public ArrayQueue(){
        size=DEFAULT_MAX_CAPACITY;
        elementData=new Object[size];
    }

    @Override
    public void add(E ele){
        enqueue(ele);
    }

    /**
     * 队列入队，头指针不动，尾指针后移
     * @param ele       入队元素
     */
    private void enqueue(E ele){
        /*
        * 队列入队：
        *   首元素入队，头尾指针都加1
        *   非首元素入队，头指针不动，尾指针后移1，即加1
        * */

        //检查元素是否为空
        checkNotNull(ele);
        //检查队列容量，队列满时不能再插入元素，会抛出异常
        checkCapacity();
        if(queueIsEmpty()) {
            //队列为空时，头指针也要加1变为0
            frontPointer++;
        }
        //尾指针自增1
        rearPointer++;
        //尾元素赋值
        elementData[rearPointer]=ele;
    }

    /**
     * 队列元素出队，尾指针不动，头指针后移（向尾指针方向）
     * @return      返回出队元素
     */
    private E dequeue(){
        if(queueIsEmpty()){
            throw new RuntimeException("queue is empty");
        }else{
            E first = getFirst();
            //已出队元素置为null
            elementData[frontPointer]=null;
            /*
             * 如果front=rear，说明以及走到了最后一个元素
             * 如果最后一个元素也出队了，那么说明队列置空了
             * 此时恢复front和rear的值为初值-1
             * */
            if(frontPointer == rearPointer) {
                frontPointer = -1;
                rearPointer = -1;
            }else{
                //头指针后移1
                frontPointer++;
            }
            //返回出队元素
            return first;
        }
    }

    private E getFirst(){
        return (E) elementData[frontPointer];
    }

    private void checkCapacity(){
        if(isFull()){
            throw new RuntimeException("queue's capacity is full");
        }
    }

    @Override
    public boolean isFull(){
        return rearPointer==size-1;
    }

    private void checkNotNull(E ele){
        if(ele == null){
            throw new RuntimeException("can't add a null element in queue");
        }
    }

    @Override
    public boolean queueIsEmpty(){
        return frontPointer==-1&&rearPointer==-1;
    }


    @Override
    public E peek(){
        return getFirst();
    }

    @Override
    public E poll(){
        return dequeue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear(){
        if(!queueIsEmpty()){
            for (Object elementDatum : elementData) {
                elementDatum=null;
            }
            frontPointer=-1;
            rearPointer=-1;
        }
    }





}
