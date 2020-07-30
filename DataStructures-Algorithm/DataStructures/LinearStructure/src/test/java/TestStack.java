import org.junit.Test;
import stack.LinkedStack;

/**
 * @author Vinfer
 * @date 2020-07-29  11:34
 **/
public class TestStack {


    @Test
    public void testStack(){
        LinkedStack<Integer> stack = new LinkedStack<>();
        for (int i = 1; i < 6; i++) {
            stack.push(i);
        }
        int len = stack.eleCount();
        System.out.println(stack.peek()+"\n");
        for (int i = 0; i < len; i++) {
            System.out.println(stack.pop());
        }
        System.out.println(stack.isEmpty());
        for (int i = 1; i < 6; i++) {
            stack.push(i);
        }
        stack.clear();
        System.out.println(stack.isEmpty());
    }


}
