package list;

/**
 * @description         单链表
 * @author Vinfer
 * @date 2020-07-14  01:54
 **/
public class UnidirectionalLinkedList<E> implements ILinkedList<E>{

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

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E ele){
        linkLast(ele);
    }

    @Override
    public void add(int index, E ele) {
        /*
        * 指定位置插入的元素，该索引也必须是合法索引
        * 因此如果对空链表使用该方法新增元素，那么index只能是0
        * */
        checkIndex(index);
        if(index==0){
            //index是0直接头插
            linkFirst(ele);
        }else{
            linkNode(index, ele);
        }
    }

    @Override
    public void addFirst(E ele){
        linkFirst(ele);
    }

    /**
     * 尾插一个元素，add方法默认使用尾插
     * 尾插头不动
     * @param ele       元素值
     */
    void linkLast(E ele){
        if(head==null){
            /*链表为空时，头结点初始化为一个新节点，并将尾结点指向头结点*/
            head = new Node<>(ele,null);
            tail=head;
        }else{
            /*头结点不为空即链表长度>1时*/
            //初始化新节点
            Node<E> newNode = new Node<>(ele,null);
            //拿到尾结点
            Node<E> lastNode = tail;
            //将尾结点的next指向新节点
            lastNode.next=newNode;
            //更新尾结点，将其指向新节点
            tail=newNode;
        }
        size++;
    }

    /**
     * 头插一个元素
     * 头插尾不动
     * @param ele       元素值
     */
    void linkFirst(E ele){
        if(head==null){
            /*链表为空时，头结点初始化为一个新节点，并将尾结点指向头结点*/
            head = new Node<>(ele,null);
            tail=head;
        }else{
            /*
            * 由于是单向链表。因此头插很简单
            * 拿到头结点，将头结点指向新节点
            * 并且该新的头结点的next指向旧的头结点
            * */
            Node<E> firstNode = head;
            head= new Node<>(ele,firstNode);
        }
        size++;
    }

    /**
     * 向头节点除外的指定位置插入新节点
     * @param index     节点位置索引
     * @param ele       节点元素值
     */
    void linkNode(int index,E ele){
        //先拿到index-1处的node
        Node<E> node = node(index-1);
        /*
        * 该操作包含了两部：
        *   初始化新节点，新节点的next直接指向node.next
        *   将node.next指向新节点，与旧的节点断联
        * */
        node.next = new Node<>(ele,node.next);
        size++;
    }

    @Override
    public void iterate(){
        if(isNotEmpty()){
            Node<E> node=head;
            while (true){
                System.out.print(node.element+"\t");
                if(hasNext(node)){
                    //遍历下一个节点
                    node=node.next;
                }else{
                    //遍历完成后换行
                    System.out.println();
                    break;
                }
            }
        }else{
            throw new RuntimeException("LinkedList is empty");
        }
    }

    @Override
    public E get(int index){
        checkIndex(index);
        return node(index).element;
    }

    @Override
    public void delete(int index){
        remove(index);
    }

    @Override
    public void set(int index, E ele){
        checkIndex(index);
        //获取index处的节点,然后替换值
        node(index).element = ele;
    }

    /**
     * 移除链表指定位置的节点
     * @param index         删除节点的位置索引
     */
    private void remove(int index){
        //检验index是否合法
        checkIndex(index);
        Node<E> node = head;
        if(index == 0){
            //index为0时删除的是头结点，直接头结点下移一位
            head = node.next;
            node = null;
        }else{
            //先拿到 index-1 处的节点，即要删除的节点的上一个节点
            node = node(index-1);
            Node<E> delNode = node.next;
            if(index == size-1){
                //如果删除的是尾结点，那么直接将尾结点指向删除节点的上一个节点，尾结点上移一位，并且next置为null
                tail = node;
                tail.next = null;
            }else{
                //将删除节点的上一个节点指向删除节点的下一个节点
                node.next = delNode.next;
            }
            //将删除节点置为null，等待GC
            delNode = null;
        }
        size--;
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

    @Override
    public boolean isNotEmpty(){
        return size!=0;
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
