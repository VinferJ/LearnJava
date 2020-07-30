package list;

/**
 * @author Vinfer
 * @description         单向环形链表
 * @date 2020-07-28  10:57
 **/
public class CircleLinkedList<E> extends SingleLinkedList<E> {

    @Override
    public void add(E ele){
        super.add(ele);
        setCircle();
    }

    @Override
    public void addFirst(E ele){
        super.addFirst(ele);
        setCircle();
    }

    @Override
    public void delete(int index){
        super.delete(index);
        setCircle();
    }

    /**
     * 删除链表中指定的节点对象（根据节点对象定位删除的节点）
     * @param node      节点对象
     */
    private void remove(Node<E> node){
        if(node == head){
            delete(0);
        }else if(node == tail){
            delete(size-1);
        }else{
            Node<E> curNode = head;
            /*遍历到删除节点的前一个节点*/
            while (curNode.next != node){
                curNode = curNode.next;
            }
            //当前节点下一个节点指向删除节点的下一个节点
            curNode.next = node.next;
            //删除的节点置为null
            node = null;
            size--;
        }
    }

    /**
     * 使普通的单链表变成环形链表
     */
    private void setCircle(){
        if(tail.next!=head){
            tail.next = head;
        }
    }

    @Override
    public void iterate(){
        if(isNotEmpty()){
            Node<E> node = head;
            while (true){
                System.out.print(node.element+"\t");
                if(node.next != head){
                    node = node.next;
                }else{
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
                if(node.next != head){
                    node = node.next;
                }else{
                    break;
                }

            }
            str.append("]");
            return str.toString();
        }
        return null;
    }

    /**
     * 约瑟夫问题，环形链表解决
     * @param k             第k个节点为起始节点
     * @param removeCount   计数至该值时，节点出队
     * @return              返回出队序列，一个整形数组
     */
    public int[] resolveJoseph(int k,int removeCount){
        /*
        * 真正的起始节点坐标，如果传入的start大于等于size，那么对size取模
        * 小于size时则直接赋值
        * 由于k表示的是第k个节点，因此变为index还需要-1
        * */
        int start = k>=size?(k%size)-1:k-1;
        //开始报数的第k个节点
        Node<E> startNode = node(start);

        /*
        * 开始节点的上一个节点
        * 使用该辅助指针进行对出队节点进行删除，
        * 不需要再使用remove(Node<E> node)方法进行删除，此方法会增加时间复杂度
        * */
        //如果start是0，说明该节点是head，head的上一个节点是tail
        Node<E> startNodePrevNode = start==0?tail:node(start-1);
        int[] exitArr = new int[size];
        int indexCount = 0,size = this.size,curK;
        System.out.println("出队元素依次为：");
        while (this.size>0){
            /*
            * 这里的i是1不是0，因为约瑟夫问题中，第k个节点从1开始报数
            * 每次都将startNode移动到当前需要出列的节点
            * */
            for (int i = 1; i < removeCount; i++) {
                /*
                * startNodePrevNode跟随startNode同时移动相同的单位
                * 那么startNodePrevNode会一直保持为startNode的前一个节点
                * */
                startNodePrevNode =startNodePrevNode.next;
                startNode = startNode.next;
            }
            /*
            * TODO  获取出队节点的下标
            *       由于节点出列后等于链表删除了一个元素
            *       此时链表中删除节点后面的节点的下标会变化（删除节点前的节点下标不受影响）
            *       所以没法通过遍历的方法获取所有节点原始的下标
            *       解决方案1：
            *           新增一个属性，该属性存储当前节点的索引下标值
            *           缺点：耗费额外的空间，并且链表头插、删除、指定索引插入等操作的时间复杂度变大
            *
            *
            * */
            exitArr[indexCount] = 0;
            indexCount++;
            //输出元素值
            System.out.print(this.size==1?startNode.element+"\n":startNode.element+" => ");
            //先拿到下一个起始节点，即当前出列的节点的下一个节点
            Node<E> nextStartNode = startNode.next;
            /*
            * 将出列节点的前一个节点的next指向出列节点的next
            * 此时startNode就等于已经出列
            */
            startNodePrevNode.next = nextStartNode;
            //更新当前节点为下一个起始节点，那么旧的startNode将会被GC，出列完成
            startNode = nextStartNode;
            //更新链表大小
            this.size--;
        }
        return exitArr;
    }

    private int getCurExitIndex(int firstExitIndex,int curK,int size){
        return (curK%size)+1==firstExitIndex?0:+(curK%size)+1;
    }


}
