package list;

/**
 * @description         单链表
 * @author Vinfer
 * @date 2020-07-14  01:54
 **/
public class UnidirectionalLinkedList<E>{

    private static class Node<E>{
        E element;
        Node<E> next;
        Node(E ele,Node<E> next){
            this.element=ele;
            this.next=next;
        }
        Node(){}
    }

    private Node<E> head;

    private Node<E> tail;

    private int size=0;

    public Node<E> getHead() {
        return head;
    }

    public void setHead(Node<E> head) {
        this.head = head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public void setTail(Node<E> tail) {
        this.tail = tail;
    }

    public int size() {
        return size;
    }


    public void add(E ele){
        linkedLast(ele);
    }

    public void addFirst(E ele){
        linkedFirst(ele);
    }

    /**
     * 尾插一个元素，add方法默认使用尾插
     * 尾插头不动
     * @param ele       元素值
     */
    void linkedLast(E ele){
        if(head==null){
            head = new Node<>(ele,null);
            tail=head;
        }else{
            Node<E> newNode = new Node<>(ele,null);
            Node<E> lastNode = tail;
            lastNode.next=newNode;
            tail=newNode;
        }
        size++;
    }

    /**
     * 头插一个元素
     * 头插尾不动
     * @param ele       元素值
     */
    private void linkedFirst(E ele){
        if(head==null){
            head = new Node<>(ele,null);
            tail=head;
        }else{
            Node<E> firstNode = head;
            head= new Node<>(ele,firstNode);
        }
        size++;
    }

    /**
     * 链表迭代：head->tail
     */
    public void iterate(){
        if(!isEmpty()){
            Node<E> node=head;
            while (true){
                System.out.println(node.element);
                if(hasNext(node)){
                    node=node.next;
                }else{
                    break;
                }
            }
        }else{
            throw new RuntimeException("LinkedList is empty");
        }
    }

    public E get(int index){
        checkIndex(index);
        return node(index).element;
    }

    private boolean hasNext(Node<E> node){
        return node.next != null;
    }

    private Node<E> node(int index){
        Node<E> node = head;
        /*遍历到index位置的node*/
        for (int i = 0; i < index; i++) {
            /*
            * 调用该方法前必定先检查index值
            * 当该index是元素下标识，直接遍历到index处
            * 不需要再另外进行hasNext判断
            * */
            node=node.next;
        }
        return node;
    }

    public boolean isEmpty(){
        return size==0;
    }

    private boolean isElementIndex(int index){
        return index>=0 && index<size;
    }

    private void checkIndex(int index){
        if(!isElementIndex(index)){
            outOfBoundsExp(index);
        }
    }

    private void outOfBoundsExp(int index){
        throw new IndexOutOfBoundsException("Index: "+index+"，Size: "+size);
    }

    /**
     * 单链表倒置
     * 单链表倒置才有意义，双链表由于有prev节点，倒置没有意义（从尾部向头结点遍历即可）
     */
    public void reverse(){
        /*tail非空且不等于head时倒置才有意义*/
        if(tail!=null&&tail!=head){
            /*
             * 单链表倒置思路：
             * 先拿到初始尾结点，该节点作为存留的temp节点
             * tail上移一个节点（变为尾结点的上一个节点）
             * 通过循环不断拿到上当前的tail的上一个节点，tail再位移
             * 直到tail=head，最后将head指向最开始的lastNode
             * */

            //初始尾结点
            Node<E> lastNode = tail;
            //中间遍历节点，即每次遍历的当前tail的上一个节点
            Node<E> tempNode;
            //当前尾结点
            Node<E> currentLastNode=tail;
            //当前尾结点的索引值
            int currentLastIndex=size-1;
            for (int i = 0; i < size-1; i++) {
                //每次都从头结点开始遍历
                tempNode=head;
                /*拿到当前tail的上一个节点*/
                for (int j = 0; j < currentLastIndex-1; j++) {
                    tempNode=tempNode.next;
                }
                //将该节点赋值给currentLastNode
                currentLastNode=tempNode;
                //将tail的next指向上一个节点
                tail.next=currentLastNode;
                //更新tail，将tail指向当前尾结点
                tail=currentLastNode;
                //更新当前尾结点的index
                currentLastIndex--;
            }
            //最终tail会与head重合，此时将tail.next置为null
            tail.next=null;
            //将head指向初始真实的尾节点lastNode
            head=lastNode;
        }
    }

}
