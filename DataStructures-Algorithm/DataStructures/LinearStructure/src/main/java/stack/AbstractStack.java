package stack;



/**
 * @author Vinfer
 * @date 2020-07-29  11:05
 **/
public abstract class AbstractStack<E> implements IStack<E>{

    int eleCount = 0;

    static class Node<E>{
        Node<E> prev;
        Node<E> next;
        E element;
        Node(Node<E> prev,E element,Node<E> next){
            this.prev = prev;
            this.next = next;
            this.element = element;
        }
    }

    Node<E> first;

    /**
     * 获取栈中元素个数
     * @return      返回元素个数
     */
    @Override
    public int eleCount(){
        return eleCount;
    }

    @Override
    public boolean isEmpty(){
        return eleCount==0;
    }

    /**
     * 头插节点
     * @param ele       元素值
     */
    void linkFirst(E ele){
        checkNotNull(ele);
        if(first == null){
            first = new Node<>(null, ele, null);
        }else{
            Node<E> newFirst = new Node<>(null, ele, first);
            /*让旧的first的prev和自身都指向新节点*/
            first.prev = newFirst;
            first = newFirst;
        }
        eleCount++;
    }

    /**
     * 获取节点的元素值
     * @return      返回节点元素值
     */
    E getFirst(){
        checkEmpty();
        return first.element;
    }

    /**
     * 移除头节点
     */
    void removeFirst(){
        checkEmpty();
        Node<E> f = first.next;
        first = null;
        first = f;
        if(f != null){
            first.prev = null;
            f = null;
        }
        eleCount--;
    }

    /**
     * 清空栈内所有节点
     */
    void clearList(){
        if(!isEmpty()){
            for (int i = 0; i < eleCount; i++) {
                removeFirst();
            }
            eleCount = 0;
        }
    }

    void checkNotNull(E ele){
        if(ele == null){
            throw new RuntimeException("Can't push a null element into stack");
        }
    }

    void checkEmpty(){
        if(isEmpty()){
            throw new RuntimeException("Stack is empty");
        }
    }

}
