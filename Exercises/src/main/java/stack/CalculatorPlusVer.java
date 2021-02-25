package stack;



/**
 * 基于原计算器的升级版，加入了对中缀表达式转换后缀表达式的处理
 * 最后基于转换的后缀表达式进行计算，大大简化计算步骤
 * @author Vinfer
 * @date 2020-08-12  10:49
 **/
public class CalculatorPlusVer extends Calculator{


    public static void main(String[] args) {
        System.out.println(calculate("((1+((2+3)*4)-5.0))"));;
    }



    public static double calculate(String expression){
        //表达式检查
        checkExpression(expression);
        //将中缀表达式转换成后缀表达式
        String postfixExpression = postfixExpTransform(expression);
        System.out.println("后缀表达式为："+postfixExpression);
        //计算值病返回
        return doCalculate(postfixExpression);
    }

    /**
     * 表达式合法检查，最严谨的检查方式是写正则表达式进行语法检查
     * 这里只做一些基本的简单检查
     * @param expression        表达式
     */
    static void checkExpression(String expression){
        String[] elements = expression.split("");
        Calculator.checkExpression(elements);
        /*检查扩号里没有表达式的情况*/
        for (int i = 0; i < elements.length; i++) {
            if(elements[i].equals(LEFT_BRACKETS) && i+1<elements.length-1){
                if(elements[i+1].equals(RIGHT_BRACKETS)){
                    invalidException("no expression between two brackets");
                }
            }
        }
    }

    /**
     * 后缀表达式值计算
     * @param postfixExp        后缀表达式
     * @return                  返回计算结果
     */
    static double doCalculate(String postfixExp){
        String[] expression = postfixExp.split(",");
        LinkedStack<String> operateStack = new LinkedStack<>();
        for (String ele : expression) {
            //ele.split("\\.").length==2是为了判断更该数字是否为浮点数
            if (isNumber(ele) || ele.split("\\.").length == 2) {
                operateStack.push(ele);
            } else {
                /*出栈两个元素进行运算，将得到的结果再重新入栈*/
                double num1 = Double.parseDouble(operateStack.pop());
                double num2 = Double.parseDouble(operateStack.pop());
                operateStack.push(String.valueOf(getCalculateResult(num1, num2, ele)));
            }
        }
        return Double.parseDouble(operateStack.pop());
    }

    /**
     * 中缀表达式转为后缀表达式
     * @param infixExp          中缀表达式
     * @return                  返回转换后带分隔符的后缀表达式
     */
    static String postfixExpTransform(String infixExp){
        LinkedStack<String> resultStack = new LinkedStack<>();
        LinkedStack<String> operatorStack = new LinkedStack<>();
        String[] chars = infixExp.split("");
        int len = chars.length;
        for (int i = 0; i < len; i++) {
            String pushEle = chars[i];
            /*
            * 符号入栈分数字和操作符，是数字时直接进入结果栈
            * 符号时入栈处理的结果与中缀式子处理类似
            * */
            if(isNumber(pushEle)){
                /*
                * 是数字时需要判断是否为连续数字或者浮点数
                * */
                if(expHasNext(len,i)){
                    if(isNumber(chars[i+1])||DOT.equals(chars[i+1])){
                        String constantRes = constantNumberHandler(infixExp, i);
                        String[] temp = constantRes.split("_");
                        int curIndex = Integer.parseInt(temp[1]);
                        //i的下一次自增的位置就是需要重新跳转遍历的字符
                        i = curIndex-1;
                        pushEle = temp[0];
                    }
                }
                resultStack.push(pushEle);
            }else{
                if(isOperator(pushEle)){
                    /*
                    * 如果入栈元素是右括号，那么将操作符栈内的元素不断出栈
                    * 直到栈顶元素为左括号时，丢弃掉这对括号
                    * */
                    if(pushEle.equals(RIGHT_BRACKETS)){
                        while (!operatorStack.peek().equals(LEFT_BRACKETS)){
                            resultStack.push(operatorStack.pop());
                            if(operatorStack.isEmpty()){
                                break;
                            }
                        }
                        /*循环结束时，此时栈顶就是左括号，再将该左括号出栈即可*/
                        operatorStack.pop();
                    }else{
                        //符号按照优先级入栈的处理
                        pushOperatorInStack(operatorStack, resultStack, pushEle);
                    }
                }else{
                    invalidException("invalid operator: "+pushEle);
                }
            }
        }
        /*
        * 将操作符栈内的所有元素出栈然后入栈到结果栈中
        * */
        while (!operatorStack.isEmpty()){
            resultStack.push(operatorStack.pop());
        }
        //拿到最终的后缀表达式并返回
        return getReverseResult(resultStack);
    }

    /**
     * 操作符入栈处理
     * @param operatorStack     操作符栈
     * @param resultStack       结果栈
     * @param pushOp            入栈元素/入栈操作符
     */
    static void pushOperatorInStack(LinkedStack<String> operatorStack,LinkedStack<String> resultStack,String pushOp){
        String topEle;
        /*
        * 同样的，栈顶元素为左括号或者栈空时直接入栈
        * */
        if (!operatorStack.isEmpty() && !operatorStack.peek().equals(LEFT_BRACKETS)) {
            topEle = operatorStack.peek();
            /*
             * 不满足上面条件的入栈元素需要比较运算符优先级
             * 一直与符号栈栈顶元素做优先级比较，直到入栈元素优先级高于栈顶元素
             * 或者遇到了左括号
             * */
            while (compareToPop(topEle, pushOp)) {
                resultStack.push(operatorStack.pop());
                /*栈空直接退出循环*/
                if(!operatorStack.isEmpty()){
                    //再次将栈顶元素赋值给topEle，更新topEle
                    topEle = operatorStack.peek();
                    /*遇到栈顶是左括号也需要退出循环*/
                    if(topEle.equals(LEFT_BRACKETS)){
                        break;
                    }
                }else {
                    break;
                }
            }
        }
        operatorStack.push(pushOp);
    }

    /**
     * 将结果栈中的元素转换成逆序的结果并返回
     * @param resultStack       结果元素栈
     * @return                  返回最终的后缀表达式
     */
    static String getReverseResult(LinkedStack<String> resultStack){
        /*
        * 由于后缀表达式存在数字粘黏的情况，因此必须要有分隔符来将每个元素隔开
        * 否则最终将无法进行数值界定，那也就没法做计算了
        * */
        String[] elements = new String[resultStack.eleCount];
        StringBuilder builder = new StringBuilder();
        /*将结果栈中所有的元素出栈并放到数组中*/
        for (int i = 0; i < elements.length; i++) {
            elements[i] = resultStack.pop();
        }
        /*数组从尾部开始遍历到头部*/
        for (int i = elements.length-1; i >= 0 ; i--) {
            if(i == 0){
                builder.append(elements[i]);
            }else{
                //加入 , 作为分隔符
                builder.append(elements[i]).append(",");
            }
        }
        return builder.toString();
    }




}
