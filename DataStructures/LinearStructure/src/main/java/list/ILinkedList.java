package list;

/**
 * @author Vinfer
 * @date 2020-07-21  01:41
 **/
public interface ILinkedList<E> {

    /**
     * 链表新增元素
     * @param ele       元素值
     */
    void add(E ele);

    /**
     * 链表头插一个元素
     * @param ele       元素值
     */
    void addFirst(E ele);

    /**
     * 删除一个节点
     * @param index     链表节点位置的索引
     */
    void delete(int index);

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
