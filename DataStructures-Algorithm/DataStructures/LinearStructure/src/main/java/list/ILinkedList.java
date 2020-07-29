package list;

/**
 * @author Vinfer
 * @date 2020-07-21  01:41
 **/
public interface ILinkedList<E> {

    /**
     * 链表新增元素
     * @param ele       节点元素值
     */
    void add(E ele);

    /**
     * 向指定位置新增元素，该index必须是合法索引
     * @param index     节点位置索引
     * @param ele       节点元素值
     */
    void add(int index,E ele);

    /**
     * 链表头插一个元素
     * @param ele       元素值
     */
    void addFirst(E ele);

    /**
     * 根据节点位置索引删除一个节点
     * @param index     链表节点位置的索引
     */
    void delete(int index);

    /**
     * 根据节点元素删除元素值相同的第一个节点
     * @param ele       节点元素值
     * @return          如果存放该元素值并删除成功，返回true，否则返回false
     */
    boolean remove(E ele);

    /**
     * 更新指定索引的节点值
     * @param index     链表节点位置的索引
     * @param ele       新的节点值
     */
    void set(int index,E ele);

    /**
     * 获取指定位置的节点值
     * @param index     链表节点位置的索引
     * @return          返回指定节点的存储节点值
     */
    E get(int index);

    /**
     * 获取链表长度
     * @return      返回一个整形长度值
     */
    int size();

    /**
     * 判断链表非空
     * @return      返回判断结果
     */
    boolean isNotEmpty();

    /**
     * 链表遍历并进行标准化输出
     */
    void iterate();


}
