package stack;

import java.util.Stack;

/**
 * 编写一个包含获取栈内最小值的函数的辅助栈MinStack
 * 要求栈的push、pop、peek、min方法的时间复杂度都必须是O(1)
 * 难点：min取最小值需要遍历栈，遍历的复杂度是O(N)，因此想要O(1)的复杂度需要借助一个辅助栈实现
 * 大佬题解：https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/solution/mian-shi-ti-30-bao-han-minhan-shu-de-zhan-fu-zhu-z/
 *
 * 本题难点： 将 min() 函数复杂度降为 O(1)O(1) ，可通过建立辅助栈实现；
 * 数据栈 A: 栈A用于存储所有元素，保证入栈 push() 函数、出栈 pop() 函数、获取栈顶 peek() 函数的正常逻辑。
 * 辅助栈 B: 栈B中存储栈A中所有非严格降序的元素，则栈A中的最小元素始终对应栈B的栈顶元素，即 min() 函数只需返回栈B的栈顶元素即可。
 *
 *
 * @author Vinfer
 * @date 2021-02-24    18:57
 **/
public class MinStack {

    Stack<Integer> stackA,stackB;

    public MinStack(){
        stackA = new Stack<>();
        stackB = new Stack<>();
    }

    public void push(int ele){
        stackA.add(ele);
        // 让辅助栈B的栈顶值始终小于栈A当前push的值
        if (stackB.empty() || stackB.peek() >= ele){
            stackB.add(ele);
        }
    }

    public int peek(){
        return stackA.peek();
    }

    public int pop(){
        Integer popFromA = stackA.pop();
        if (popFromA.equals(stackB.peek())){
            stackB.pop();
        }
        return popFromA;
    }

    public int min(){
        return stackB.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(134);
        minStack.push(7980);
        minStack.push(-1320);
        minStack.push(130);
        minStack.push(-10);
        System.out.println(minStack.min());
    }

}
