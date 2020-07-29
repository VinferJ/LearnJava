package list;

/**
 * @author Vinfer
 * @date 2020-07-28  09:21
 **/
public abstract class AbstractLinkedList<E> implements ILinkedList<E>{

    /** 链表大小，初始为0*/
    int size=0;

    /**
     * 链表新增元素
     * @param ele       节点元素值
     */
    @Override
    public abstract void add(E ele);

    /**
     * 链表头插一个元素
     * @param ele       元素值
     */
    @Override
    public abstract void addFirst(E ele);

    /**
     * 更新指定节点的元素值
     * @param index     链表节点位置的索引
     * @param ele       新的节点值
     */
    @Override
    public abstract void set(int index,E ele);

    /**
     * 获取指定位置节点的元素值
     * @param index 链表节点位置的索引
     * @return      返回指定节点的元素值
     */
    @Override
    public abstract E get(int index);

    /**
     * 删除指定位置的节点
     * @param index     链表节点位置的索引
     */
    @Override
    public abstract void delete(int index);

    /**
     * 移除相同元素值的第一节点
     * @param ele       节点元素值
     * @return          存在该元素值并删除成功返回true，否则返回false
     */
    @Override
    public abstract boolean remove(E ele);

    /**
     * 链表遍历输出
     */
    @Override
    public abstract void iterate();

    @Override
    public boolean isNotEmpty(){
        return size>0;
    }

    @Override
    public int size(){
        return size;
    }

    /**
     * 根据节点对象获取该节点的索引下标
     * @param node      链表节点对象
     * @return          返回节点对象的索引下标
     */
    abstract int getNodeIndex(Object node);

    boolean isElementIndex(int index){
        return index>=0 && index<size;
    }

    void checkIndex(int index){
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException("Size: "+size+", Index: "+index);
        }
    }

    /**
     * 与指定位置的节点断开链接，从链表中移除该节点
     * @param index         节点位置下标
     */
    abstract void unlinkNode(int index);

    /**
     * 移除链表中第一个相等的节点对象
     * @param node          链表节点对象
     */
    void unlinkNode(Object node){
        unlinkNode(getNodeIndex(node));
    }

    /**
     * 根据节点位置下标获取节点对象
     * @param index         节点位置下标
     * @return              返回一个链表节点对象
     */
    abstract Object node(int index);

}
