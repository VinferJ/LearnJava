package list;

/**
 * @author Vinfer
 * @description     双向链表
 * @date 2020-07-20  03:23
 **/
public class BidirectionalLinkedList<E> implements ILinkedList<E>{


    static class Node<E>{
        Node<E> prev;
        Node<E> next;
        E element;
        Node(Node<E>prev,E ele,Node<E>next){
            this.prev=prev;
            this.element=ele;
            this.next=next;
        }
        Node(E ele){
            element=ele;
        }
        Node(){}
    }

    Node<E> head;

    Node<E> tail;

    int size;

    @Override
    public void add(E ele){
        linkLast(ele);
    }

    @Override
    public void add(int index, E ele) {
        checkIndex(index);
        if(index==0){
            linkFirst(ele);
        }else{
            linkNode(index,ele);
        }
    }

    @Override
    public void addFirst(E ele){
        linkFirst(ele);
    }

    @Override
    public void delete(int index){
        checkIndex(index);
        if(index==0){
            //index为0，删除头结点
            unlinkFirst();
        }else if(index==size-1){
            //index为size-1，删除尾结点
            unlinkLast();
        }else{
            //删除指定index的节点（头尾除外）
            unlinkNode(index);
        }
        size--;
    }

    @Override
    public void set(int index, E ele){
        checkIndex(index);
        node(index).element = ele;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).element;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 头插一个新节点
     * @param ele       节点元素值
     */
    void linkFirst(E ele){
        //初始化一个新节点
        Node<E> newNode = new Node<>(null,ele,null);
        if(head==null){
            //头结点为空时，头结点指向新节点，头尾关联
            head = newNode;
            tail = head;
        }else{
            //拿到头结点
            Node<E> firstNode = head;
            //头结点指向新节点，更新头结点
            head=newNode;
            //此时新的头结点的next指向原来旧的头结点
            head.next=firstNode;
            //旧的头结点(第二个节点)的prev指向新的头结点
            firstNode.prev=head;
        }
        size++;
    }

    /**
     * 尾插一个新节点
     * @param ele       节点元素值
     */
    void linkLast(E ele){
        Node<E> newNode = new Node<>(null,ele,null);
        if(head==null){
            head = newNode;
            tail = head;
        }else{
            //获取尾结点
            Node<E> lastNode = tail;
            /*
            * 更新尾结点：将尾结节点指向新节点
            * 并且将新的尾结点的prev指向旧的尾节点
            * */
            tail = newNode;
            tail.prev=lastNode;
            //将旧的尾结点的next指向新的尾结点
            lastNode.next=tail;
        }
        size++;
    }

    /**
     * 向除头节点以外的节点处插入新元素
     * @param index         节点位置索引
     * @param ele           节点元素值
     */
    void linkNode(int index,E ele){
        //拿到插入位置的前一个节点（index-1）
        Node<E> node = node(index-1);
        /*
        * 此操作包含3步：
        *   1. 初始化一个新节点
        *   2. 新节点的prev指向node，next指向node.next
        *   3. node.next指向该新节点
        * */
        node.next = new Node<>(node,ele,node.next);
        /*
        * 还需要将新插入节点的下一个节点的prev指向该插入节点
        * 由于node.next已经指向了新插入的节点，
        * 因此新插入节点是node.next，
        * 而新插入节点的下一个节点是node.next.next
        * */
        node.next.next.prev = node.next;
        size++;
    }

    /**
     * 移除链表头结点
     */
    void unlinkFirst(){
        //拿到头结点
        Node<E> node = head;
        //将头结点指向下一个节点
        head = head.next;
        //将新的头结点的prev置为null，与初始头结点断开
        head.prev = null;
        //将初始头结点置为null，等待GC
        node = null;
    }

    /**
     * 移除链表尾结点
     */
    void unlinkLast(){
        //拿到尾结点
        Node<E> node = tail;
        //将尾结点指向前一个节点
        tail = tail.prev;
        //将新的尾结点的next置为null，与初始尾结点断开
        tail.next = null;
        //将初始尾结点置为null
        node = null;
    }

    /**
     * 移除链表非头尾的指定位置的一个节点
     * @param index     移除的节点位置索引
     */
    void unlinkNode(int index){
        //先拿到要移除的节点的上一个节点
        Node<E> node = node(index - 1);
        //初始化要删除的节点
        Node<E> delNode = node.next;
        /*
        * 先关联节点，再断联节点:
        *   将要删除的节点的上一个节点的next指向要删除节点的下一个节点(next)
        *   再将要删除的节点的一个节点的prev指向要删除节点的上一个节点(node)
        * */
        node.next = delNode.next;
        delNode.next.prev = node;
        //将要删除的节点置为null，等待GC
        delNode = null;
    }


    @Override
    public void iterate(){
        if(isNotEmpty()){
            Node<E> node=head;
            while (true){
                System.out.print(node.element+"\t");
                if(hasNext(node)){
                    node=node.next;
                }else{
                    System.out.println();
                    break;
                }
            }
        }else{
            throw new RuntimeException("LinkedList is empty");
        }
    }

    /**
     * 逆序遍历输出链表元素
     */
    public void iterateByDesc(){
        if(isNotEmpty()){
            Node<E> node = tail;
            while (true){
                System.out.print(node.element+"\t");
                if(hasPrev(node)){
                    node=node.prev;
                }else{
                    System.out.println();
                    break;
                }
            }
        }else{
            throw new RuntimeException("LinkedList is empty");
        }
    }

    @Override
    public boolean isNotEmpty(){
        return size != 0;
    }

    /**
     * 根基索引值获取指定节点
     * @param index         指定的节点位置索引
     * @return              返回一个双链表节点
     */
    private Node<E> node(int index){
        //获取头结点
        Node<E> node = head;
        //遍历到index处
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private boolean hasNext(Node<E> node){
        return node.next!=null;
    }

    private boolean hasPrev(Node<E> node){
        return node.prev!=null;
    }

    private boolean isElementIndex(int index){
        return index>=0 && index<size;
    }

    private void checkIndex(int index){
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException("Index: "+index+"，Size: "+size);
        }
    }

}
