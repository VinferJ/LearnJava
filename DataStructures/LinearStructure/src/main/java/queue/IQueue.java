package queue;

/**
 * @author Vinfer
 * @description     队列api接口
 * @date 2020-07-21  19:13
 **/
public interface IQueue<E> {

    /**
     * 队列新增一个元素
     * @param ele       入队元素
     */
    void add(E ele);

    /**
     * 获取队头元素，但元素不出队
     * @return          返回队头元素
     */
    E peek();

    /**
     * 获取队头元素，且元素出队
     * @return          返回队头元素
     */
    E poll();

    /**
     * 获取队列大小
     * @return          返回队列长度
     */
    int size();

    /**
     * 满队判断
     * @return      满队返回true，否则返回false
     */
    boolean isFull();

    /**
     * 队列非空判断
     * @return      队空返回true，非空返回false
     */
    boolean queueIsEmpty();

    /**
     * 清空队列
     */
    void clear();

}
