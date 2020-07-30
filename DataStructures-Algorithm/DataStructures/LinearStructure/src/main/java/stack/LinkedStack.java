package stack;

/**
 * @author Vinfer
 * @date 2020-07-29  11:12
 **/
public class LinkedStack<E> extends AbstractStack<E> implements IStack<E> {


    @Override
    public void push(E ele) {
        linkFirst(ele);
    }

    @Override
    public E peek() {
        return getFirst();
    }

    @Override
    public E pop() {
        E ele = getFirst();
        removeFirst();
        return ele;
    }

    @Override
    public void clear() {
        clearList();
    }
}
