package stack;

/**
 * TODO 数组实现栈
 *
 * @author Vinfer
 * @date 2021-02-24    18:47
 **/
public class ArrayStack {

    private int size;

    private static final int DEFAULT_MAX_CAPACITY = 1000;

    private int frontPointer=-1;

    private int rearPointer=-1;

    private final int[] elementData;

    public ArrayStack(){
        this(DEFAULT_MAX_CAPACITY);
    }

    public ArrayStack(int initCapacity){
        size = initCapacity;
        elementData = new int[size];
    }

    public void push(int ele){

    }

    public int pop(){
        return 0;
    }

    public int peek(){
        return 0;
    }

}
