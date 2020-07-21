package queue;

/**
 * @author Vinfer
 * @description     队列-基于数组实现
 * @date 2020-07-20  16:35
 **/
public class ArrayQueue<E>{

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

    public void add(E ele){
        enqueue(ele);}

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
        if(frontPointer==-1||rearPointer==-1) {
            frontPointer++;
        }
        rearPointer++;
        elementData[rearPointer]=ele;
    }

    private E dequeue(){
        if(queueIsEmpty()){
            throw new RuntimeException("queue is empty");
        }else{
            E first = (E) elementData[frontPointer];
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
                frontPointer++;
            }
            return first;
        }
    }

    private E getFirst(){
        if(queueIsEmpty()){
            throw new RuntimeException("queue is empty");
        }else{
            return (E) elementData[frontPointer];
        }
    }

    private void checkCapacity(){
        if(isFull()){
            throw new RuntimeException("queue's capacity is full");
        }
    }

    private boolean isFull(){
        return rearPointer==size-1;
    }

    private void checkNotNull(E ele){
        if(ele == null){
            throw new RuntimeException("can't add a null element in queue");
        }
    }

    private boolean queueIsEmpty(){
        return frontPointer==-1&&rearPointer==-1;
    }

    /**
     * 取出队头元素但是该队头元素不出队
     * @return      返回队列首元素
     */
    public E peek(){
        return getFirst();
    }

    /**
     * 获取队头元素，并且队头元素出队
     * @return      返回队列头部元素
     */
    public E poll(){
        return dequeue();
    }

    /**
     * 清空队列
     */
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
