package recursion;

/**
 * 递归算法：
 * 概念：通俗来讲，递归就是方法自己调用自己
 *      在方法执行时，每个方法都会拥有自己独立的方法栈
 *      因此每次递归调用时，方法参数都是全新的额副本，
 *      并且由于栈的FILO特性，当在方法中遇到递归调用时
 *      方法会进入下一个递归中，当该递归完成并且返回时
 *      返回到上一个方法中，此时该方法会继续执行未执行完成的语句
 *      知道方法结束进行返回
 * 递归的优点：
 *      1. 提高代码简洁度，并且更易理解
 *      2. 可以利用栈的特性，以更优雅的方式解决一些复杂问题
 * 递归的缺点：
 *      1. 每次递归都要开辟新的栈空间，递归次数过大时会造成OOM
 *      2. 递归的效率低
 *
 * @author Vinfer
 * @date 2020-09-02    22:08
 **/
public class Recursion {

    private static final int BORDER = 2;

    public static void main(String[] args) {
        recursivePrint(4);
        System.out.println(recursiveFactorial(4));
    }

    static void recursivePrint(int num){
        //System.out.println(num);  位置不同的输出，结果也不同
        if(num >= BORDER){
            recursivePrint(num-1);
        }
        System.out.println(num);
    }

    static int recursiveFactorial(int num){
        if( num == 1){
            return 1;
        }else {
            return num * recursiveFactorial(num - 1);
        }
    }


}
