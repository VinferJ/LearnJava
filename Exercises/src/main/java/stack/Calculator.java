package stack;


import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 使用栈来实现一个简单的计算器
 * 该计算器允许整数（包括负数）、浮点数的运算
 * 且能够处理连续的运算符输入，如：2+++---+1，优化为：2-1，
 * @author Vinfer
 * @date 2020-07-29  12:23
 **/
public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp1 = "22.187+-1.187+-2*3.5+-6/2.0--2.5";
        String exp2 = "18+(5*3-1)-9";
        String regex = "[^(0-9)]";
        System.out.println(Arrays.toString(exp1.split("")));
        Pattern pattern = Pattern.compile(regex);
        String[] split = pattern.split(exp1);
        System.out.println(Arrays.toString(split));
        System.out.println(22.187-1.187-2*3.5-3+2.5);
        System.out.println(calculate(exp1));
    }


    static final String[] NUMBERS = {"0","1","2","3","4","5","6","7","8","9"};
    static final String[] OPERATORS = {"+","-","*","/","(",")"};
    static final String LEFT_BRACKETS = "(";
    static final String RIGHT_BRACKETS = ")";
    static final String DOT = ".";
    static final String MINUS_SIGN = "-";
    static final String PLUS_SIGN = "+";
    static final String MUL_SIGN = "*";
    static final String DIVISION_SIGN = "/";


    /**
     * 根据传入的运算表达式，计算式子结果
     * TODO     括号运算：
     *              1. 括号合法检查（是否出现孤独括号，括号范围是否正确）
     *              2. 多重括号，多对括号运算
     *          首字符为运算符的处理逻辑：
     *              1. + 或 -
     *              2. (
     *
     * @param expression        运算表达式
     * @return                  返回浮点型结果
     */
    public static double calculate(String expression){
        //先进行字符分割
        String[] elements = expression.split("");
        //表达式合法检查
        checkExpression(elements);
        //运算数栈
        LinkedStack<String> numberStack = new LinkedStack<String>();
        //运算符栈
        LinkedStack<String> operatorStack = new LinkedStack<String>();
        for (int i = 0; i <elements.length; i++) {
            //取出当前遍历元素值，方便之后会对其重新赋值
            String pushEle = elements[i];
            /*
            * 如果当前字符是数字，那么还需要再判断该数字是否为连续的数字
            * */
            if(isNumber(pushEle)){
                if(i+1<=elements.length-1 && (isNumber(elements[i+1] ) || DOT.equals(elements[i+1]))){
                    //拿到连续数字和exp遍历到的下标的拼接字符串
                    String constantNum = constantNumberHandler(expression, i);
                    //字符串分隔，temp[0]是真正的连续数字，而temp[1]是exp遍历到的下标
                    String[] temp = constantNum.split("_");
                    constantNum = temp[0];
                    int curIndex = Integer.parseInt(temp[1]);
                    //由于i在下一次循环中还要自增1，所以这里赋值为curIndex-1
                    i = curIndex-1;
                    //将连续数字入栈
                    numberStack.push(constantNum);
                }else{
                    numberStack.push(pushEle);
                }
            }else{
                /*如果运算符是 ( 直接进栈*/
                if(LEFT_BRACKETS.equals(pushEle)){
                    operatorStack.push(pushEle);
                }else if(RIGHT_BRACKETS.equals(pushEle)){
                    /*如果是 ) 将当前所有已入栈的数进行计算并保存*/
                    calculateAllInStack(numberStack, operatorStack);
                } else {
                    /*
                     * 检查是否存在连续符号，进行运算符优化，转换为等价的单个运算符
                     * 连续符号为 ( 不需要优化
                     * */
                    if(expHasNext(expression.length(), i) && isOperator(elements[i+1]) && !LEFT_BRACKETS.equals(elements[i+1])){
                        String constantOp = constantOperatorHandler(expression, i);
                        String[] temp = constantOp.split("_");
                        constantOp = temp[0];
                        int curIndex = Integer.parseInt(temp[1]);
                        /*
                         * 如果返回的constantOp是空串，说明遇到了 *+-...-+ 或 /+-...-+ 这两种情况
                         * 此时已经将正确的操作符和操作数入栈,这里只需要将遍历下标重新赋值
                         * */
                        if("".equals(constantOp)){
                            i = curIndex;
                            //不再走下面的元素入栈判断，重新进入下一个循环
                            continue;
                        }else{
                            //优化后的运算符
                            pushEle = constantOp;
                            /*
                             * 由于连续操作符的判断中，不断取curIndex的下一个元素判断是否为运算符
                             * 因此curIndex对应的元素还是一个运算符，所以这里的curIndex直接赋值给i，不需要再减1
                             * */
                            i = curIndex;
                        }
                    }
                    /*
                     * 运算符入栈，当栈空或者栈顶元素是 ) 时，运算符直接入栈
                     * 否则需要与栈顶元素进行优先级比较
                     * */
                    if (!operatorStack.isEmpty() && !LEFT_BRACKETS.equals(operatorStack.peek())) {
                        String topEle = operatorStack.peek();
                        /*
                         * 比较栈顶运算符与入栈运算符的优先级
                         * 当入栈运算符优先级低于或等于栈顶运算符时需要出栈运算
                         * */
                        if (compareToPop(topEle, pushEle)) {
                            doOneCalculate(numberStack, operatorStack);
                            /*
                             * 此时还需要在同栈顶运算符做优先级比较
                             * 如果还满足出栈运算条件，那么仍需出栈运算后再入栈
                             * 而这里只需要再多做这一次比较，因为不会存在两个相邻的同级运算符
                             * 存在于运算符栈内
                             * */
                            if (!operatorStack.isEmpty() && compareToPop(operatorStack.peek(), pushEle)) {
                                doOneCalculate(numberStack, operatorStack);
                            }
                        }
                    }
                    operatorStack.push(pushEle);
                }

            }
        }
        //运算表达式的所有元素都已经入两栈，此时进行最终计算，完成后numberStack中顶部元素就是最终式子的结果
        calculateAllInStack(numberStack, operatorStack);
        return Double.parseDouble(numberStack.pop());
    }

    static boolean expHasNext(int length,int curIndex){
        return curIndex+1<=length-1;
    }

    static void checkExpression(String[] expression){
        String firstEle = expression[0];
        String lastEle = expression[expression.length-1];
        /*
        * 1. 如果表达式以操作符结尾且不是 )，那么表达式非法
        * 2. 如果表达式以除了 ( + - 外的操作符开头，那么表达式非法
        * 3. 如果表达式包含非法字符，那么表达式非法
        * */
        if(isOperator(lastEle) && !lastEle.equals(RIGHT_BRACKETS)){
            invalidException("end with operator but it is not a ')'");
        }
        if(isOperator(firstEle)){
            switch (firstEle){
                case "+":
                case "-":
                case "(":
                    break;
                default:invalidException("start with operator but it is not a '(' or '+' or '-'");
            }
        }
        String numberAndOperator = "0123456789+-*/().";
        for (String s : expression) {
            if (!numberAndOperator.contains(s)) {
                invalidException("expression contains invalid character: '"+s+"'");
            }
        }
    }

    static void invalidException(String msg){
        throw new RuntimeException("Invalid expression: "+msg);
    }

    /**
     * 比较栈内运算符与入栈运算符的优先级
     * 当入栈运算符的优先级低于或等于栈内运算符时，需要出栈进行运算
     * 如果是 ( 时，运算符直接入栈，后续运算符入栈仍需比较
     * 当遇到 ) 时，将运算符栈的所有符号出栈进行运算
     * @param topOp         栈顶运算符
     * @param pushOp        入栈运算符
     * @return              返回是否出栈的结果
     */
    static boolean compareToPop(String topOp,String pushOp){
        if (topOp.equals(pushOp) || RIGHT_BRACKETS.equals(pushOp)) {
            //两运算符相同，意味着优先级相等，返回true
            return true;
        }else{
            int topOpLevel = getOperatorLevel(topOp);
            int pushOpLevel = getOperatorLevel(pushOp);
            //当入栈运算符的优先级小于等于栈顶的运算符时，需要出栈运算
            return pushOpLevel<=topOpLevel;
        }
    }

    static String constantOperatorHandler(String expression,int curIndex){
        int len = expression.length();
        String curOp = String.valueOf(expression.charAt(curIndex));
        String op;
        String constantOp = "";
        while (expHasNext(len, curIndex) && isOperator(String.valueOf(expression.charAt(curIndex+1)))){
            //获取下一个运算符
            op = String.valueOf(expression.charAt(++curIndex));
            constantOp = curOp+op;
            //检查是否符合连续运算符规范
            checkConstantOperator(curOp,op);
            //将连续的运算符等价为单个运算符
            switch (constantOp){
                case "++":
                case "--":
                    constantOp = "+";
                    break;
                case "+-":
                case "-+":
                    constantOp = "-";
                    break;
                default:break;
            }
            curOp = constantOp;
        }
        /*拼接字符串，将最终的操作符以及遍历到的下标一并返回*/
        return constantOp + "_" + curIndex;
    }

    static String constantNumberHandler(String expression, int curIndex){
        StringBuilder constantNum = new StringBuilder();
        while (isNumber(String.valueOf(expression.charAt(curIndex))) || DOT.equals(String.valueOf(expression.charAt(curIndex)))){
            constantNum.append(expression.charAt(curIndex));
            curIndex++;
            //防止空指针异常
            if(curIndex>expression.length()-1){
                break;
            }
        }
        /*
        * 需要返回该连续数字以及当前遍历到的字符下标
        * 因此使用 _ 进行拼接一并返回
        * */
        constantNum.append("_").append(curIndex);
        return constantNum.toString();
    }

    static void checkConstantOperator(String firstOp,String secondOp){
        /*
        * 非法连续运算符：
        *       1. 以 * 或 / 开头
        *       2. 以 ) 或 * 或 / 结尾
        * */
        if(secondOp.equals(RIGHT_BRACKETS) || secondOp.equals(MUL_SIGN) || secondOp.equals(DIVISION_SIGN)){
            invalidException("invalid constant operator: '"+firstOp+secondOp+"'");
        }else if(firstOp.equals(MUL_SIGN) || firstOp.equals(DIVISION_SIGN)){
            invalidException("invalid constant operator: '"+firstOp+secondOp+"'");
        }
    }

    static void doOneCalculate(LinkedStack<String>numberStack,LinkedStack<String>operatorStack){
        /*
        * 运算数栈出栈两个元素，运算符栈出栈一个元素，运算后将结果保存到运算数栈中，
        * 然后当前需要入栈的运算符再入栈
        **/
        double num1 = Double.parseDouble(numberStack.pop());
        double num2 = Double.parseDouble(numberStack.pop());
        String operator = operatorStack.pop();
        numberStack.push(String.valueOf(getCalculateResult(num1, num2, operator)));
    }

    /**
     * 获取两个操作数的运算值
     * @param num1          左操作数
     * @param num2          右操作数
     * @param operator      操作运算符
     * @return              返回计算结果
     */
    static double getCalculateResult(double num1, double num2, String operator){
        /*num2是后出栈的元素，运算位置需要在num1的左边*/
        switch (operator){
            case "+": return num2+num1;
            case "-": return num2-num1;
            case "*": return num2*num1;
            case "/": return num2/num1;
            default:return 0;
        }
    }

    static void calculateAllInStack(LinkedStack<String>numberStack,LinkedStack<String>operatorStack){
        while (!operatorStack.isEmpty()){
            String operator = operatorStack.pop();
            /*当前出栈的顶部元素不是 ( 时才进行运算*/
            if(!operator.equals(LEFT_BRACKETS)){
                double num1 = Double.parseDouble(numberStack.pop());
                double num2 = Double.parseDouble(numberStack.pop());
                numberStack.push(String.valueOf(getCalculateResult(num1, num2, operator)));
            }else{
                //遇到左括号时直接退出
                break;
            }
        }
    }

    static int getOperatorLevel(String operator){
        switch (operator){
            case "+":
            case "-":
                return 1;
            case "*" :
            case "/" :
                return 2;
            case "(":
            case ")":
                return 3;
            default:return 0;
        }
    }

    static boolean isNumber(String str){
        for (String s : NUMBERS) {
            if(str.equals(s)){
                return true;
            }
        }
        return false;
    }

    static boolean isOperator(String str){
        for (String operator : OPERATORS) {
            if(str.equals(operator)){
                return true;
            }
        }
        return false;
    }

}
