package stack;

/**
 * @author Vinfer
 * @description     栈
 * @date 2020-07-29  11:00
 **/
public interface IStack<E> {

    /**
     * 元素入栈
     * @param ele       入栈元素值
     */
    void push(E ele);

    /**
     * 获取栈顶元素
     * @return      返回一个栈顶元素
     */
    E peek();

    /**
     * 获取栈顶元素并且栈顶元素出栈
     * @return      返回一个栈顶元素
     */
    E pop();

    /**
     * 清空整个栈
     */
    void clear();

    /**
     * 获取当前栈内元素个数
     * @return      返回栈中元素总数
     */
    int eleCount();

    boolean isEmpty();

}
