package list;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
import sort.SimpleSort;

/**
 * @description         单链表
 * @author Vinfer
 * @date 2020-07-14  01:54
 **/
public class SingleLinkedList<E> extends AbstractLinkedList<E> implements ILinkedList<E>{

    /**
     * 链表节点对象
     * @param <E>   节点存储数据类型
     */
    static class Node<E>{
        /** 节点元素值 */
        E element;

        /** 该节点指向的下一个节点对象 */
        Node<E> next;

        Node(E ele,Node<E> next){
            this.element=ele;
            this.next=next;
        }
        Node(){}
    }

    /** 链表头结点，头结点可存放数据也可不存放数据，这里选择存放数据 */
    Node<E> head;

    /** 链表尾结点 */
    Node<E> tail;


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
        /*
        * 如果头结点也存放数据，那么每次插入都必须判断head是否为空
        * 如果head不存放数据，那么每次插入都不需要判断head是否空，头插也一样
        * 就是需要额外的一个节点作为head节点
        * */
        if(head==null){
            /*链表为空时，头结点初始化为一个新节点，并将尾结点指向头结点*/
            initHead(ele);
        }else{
            /*头结点不为空即链表长度>1时*/
            //初始化新节点
            Node<E> newNode = new Node<>(ele,null);
            //将尾结点的next指向新节点
            tail.next=newNode;
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
            initHead(ele);
        }else{
            /*
            * 由于是单向链表。因此头插很简单
            * 拿到头结点，将头结点指向新节点
            * 并且该新的头结点的next指向旧的头结点
            * */
            head = new Node<>(ele, head);
        }
        size++;
    }

    void initHead(E ele){
        head = new Node<>(ele, null);
        tail = head;
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
    public String toString(){
        StringBuilder str = new StringBuilder();
        Node<E> node = head;
        str.append("[ ");
        if(isNotEmpty()){
            while (true){
                str.append(node.element).append(" ");
                if(node.next==null){
                    break;
                }
                node = node.next;
            }
            str.append("]");
            return str.toString();
        }
        return null;
    }

    @Override
    public E get(int index){
        checkIndex(index);
        return node(index).element;
    }

    @Override
    public void delete(int index){
        unlinkNode(index);
    }

    @Override
    public boolean remove(E ele) {
        if(ele == null){
            for(Node<E> node = head; node != null; node = node.next){
                if(node.element == null){
                    unlinkNode(node);
                    //只删除第一个元素值相等的节点
                    return true;
                }
            }
        }else{
            for(Node<E> node = head; node != null; node = node.next){
                if(node.element.equals(ele)){
                    unlinkNode(node);
                    //只删除第一个元素值相等的节点
                    return true;
                }
            }
        }
        return false;
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
    @Override
    void unlinkNode(int index){
        //检验index是否合法
        checkIndex(index);
        Node<E> node = head;
        //index为0时删除的是头结点，直接头结点下移一位
        if(index == 0){
            /*先将旧的head置为null，再指向新的head，此时旧的head等待GC*/
            head = null;
            head = node.next;
        }else{
            //先拿到 index-1 处的节点，即要删除的节点的上一个节点
            node = node(index-1);
            Node<E> delNode = node.next;
            if(index == size-1){
                //如果删除的是尾结点，那么直接将尾结点指向删除节点的上一个节点，尾结点上移一位，并且next置为null
                //旧的tail等待GC
                tail = null;
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

    /*void unlinkNode(Node<E> node){
        unlinkNode(getNodeIndex(node));
    }*/

    @Override
    int getNodeIndex(Object node){
        int indexCount = 0;
        Node<E> h = head;
        if(node == tail){
            return size-1;
        }
        while (h != node){
            h = h.next;
            indexCount++;
        }
        return indexCount;
    }

    boolean hasNext(Node<E> node){
        return node.next != null;
    }

    @Override
    Node<E> node(int index){
        Node<E> node = head;
        int count = 0;
        /*遍历到index位置的node*/
        while (count++<index){
            /*
            * 调用该方法前必定先检查index值
            * 当该index是元素下标识，直接遍历到index处
            * 不需要再另外进行hasNext判断
            * */
            node = node.next;
        }
        return node;
    }

    /**
     * 删除头结点，并返回该节点元素值
     * @return          返回顶部节点元素值
     */
    public E pop(){
        if(isNotEmpty()){
            Node<E> node = head;
            //拿到当前头结点的节点元素值
            E ele = node.element;
            //将头结点指向下一个节点
            head = node.next;
            //将旧的头结点置为null，等待GC
            node = null;
            if(head==null){
                tail = null;
            }
            size--;
            return ele;
        }else{
            throw new RuntimeException("List is empty");
        }

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

            /*
            * O(N^2)复杂度的链表倒置，空间开销小，只需要3个中间节点
            * */
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

    /**
     * 低时间复杂度的链表倒置
     */
    public void reverseByArr(){
        if(tail!=head && head!=null){
            /*
            * 使用中间数组的链表倒置方式，耗费额外的空间（空间开销大，需要存储链表元素的数组）
            * 但是可以把时间复杂度降到O(N)
            * */
            Object[] eleArr = new Object[size];
            int len = size;
            for (int i = 0; i < len; i++) {
                /*
                * 将链表元素一个个出栈，并将这些元素从尾部至头部
                * 存放到中间数组中，此时完成了元素逆序存储，此时链表元素
                * 以及全部出栈，链表为空
                * */
                eleArr[len-1-i] = pop();
            }
            /*将中间数组的值重新放入链表*/
            for (int i = 0; i < len; i++) {
                /*
                * 再将中间数组的元素顺序取出并尾插到链表中
                * */
                linkLast((E) eleArr[i]);
            }
        }
    }


    /**
     * 使用头插进行连表倒置
     */
    public void reverseByLinkFirst(){
        /*
        * 通过头插节点进行链表逆置：
        * 头结点不动，从第二个开始，通过不断取出该节点并进行头插
        * 最终完成链表倒置
        * 由于头插完之后需要删除节点，而删除节点需要在循环内删除
        * 该链表倒置的方法时间复杂度依旧为O(N^2),但是空间开销降为了两个中间节点
        * */
        if(head!=null && head.next!=null){
            //保存旧的头结点的下一个节点（第二个节点）
            Node<E> currentHead = head.next;
            //节点删除计数，总是从2开始（因为遍历是从第二个节点开始）
            int removeCount = 2;
            for (int i = 0; i < size - 1; i++) {
                //获取当前需要头插的节点
                Node<E> node = currentHead;
                //头插节点
                linkFirst(node.element);
                //移除已经头插的节点
                unlinkNode(removeCount);
                //将中间节点指向下一个节点
                currentHead = currentHead.next;
                //删除计数自增1
                removeCount++;
            }
        }
    }

    /**
     * 从某个节点开始逆序遍历，使用递归进行遍历
     * @param startIndex    开始遍历的链表节点位置索引
     * @return              返回一个节点索引值
     */
    public int iterateByDesc(int startIndex){
        int currentIndex;
        if(startIndex!=size-1){
            currentIndex = iterateByDesc(startIndex+1);
        }
        currentIndex = startIndex;
        System.out.print(node(currentIndex).element+" ");
        return startIndex;
    }

    /**
     * 非递归逆序遍历链表，时间复杂度为O(N^2)
     */
    public void descIterate(){
        System.out.println();
        for (int i = size-1; i >=0 ; i--) {
            System.out.print(node(i).element+" ");
        }
        System.out.println();
    }

    /**
     * 整型链表元素排序
     */
    public void sort(){
        E ele = head.element;
        /*不是整型数据，不允许排序*/
        if(ele instanceof Integer){
            /*
            * 链表排序思路：
            *   将链表元素弹出，存放到整型中间数组中
            *   将该数组用冒泡排序进行排序
            *   将排序好的元素尾插到原链表中
            *   排序的时间复杂度为O(N^2)+2*O(N),且空间开销较大
            * */
            Integer[] eleArr = new Integer[size];
            int len = size;
            for (int i = 0; i < len; i++) {
                eleArr[i] = (Integer) pop();
            }
            /*冒泡排序*/
            SimpleSort.bubbleSort(eleArr);
            for (int i = 0; i < len; i++) {
                linkLast((E) eleArr[i]);
            }
            //将数组置为null，等待GC
            eleArr = null;
        }else{
            throw new RuntimeException("Only sort when data type is integer");
        }
    }

    /**
     * 链表合并，合并后的链表有序
     * @param list     合并的链表
     */
    public void combination(SingleLinkedList<Integer> list){
        //链表连接
        tail.next = (Node<E>) list.head;
        size+=list.size;
        sort();
    }

    /**
     * 合并两个无序整型链表，合并后链表有序
     * @param list1         合并链表1
     * @param list2         合并链表2
     * @return              返回一个有序的整型链表
     */
    public static SingleLinkedList<Integer> combination(SingleLinkedList<Integer> list1, SingleLinkedList<Integer> list2){
        SingleLinkedList<Integer> targetList =  new SingleLinkedList<>();
        /*
        * 用于直接用链表指针连接链表会导致list1结构被修改
        * 因此需要遍历连链表的值再进行排序合并
        * */
        int len = list1.size+list2.size;
        int indexCount = 0;
        int[] eleArr = new int[len];
        for (int i = 0; i < list1.size; i++) {
            eleArr[i] =  list1.node(i).element;
        }
        for (int i = list1.size; i < len; i++) {
            eleArr[i] =  list2.node(indexCount).element;
            indexCount++;
        }
        /*冒泡排序*/
        SimpleSort.bubbleSort(eleArr);
        for (int i = 0; i < len; i++) {
            targetList.linkLast(eleArr[i]);
        }
        return targetList;
    }


}
