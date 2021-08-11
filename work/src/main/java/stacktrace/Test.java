package stacktrace;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Jiang wenfa
 * @date 2021-07-14 11:58
 */
public class Test {


    public static void main(String[] args) {
        //method1();
        String[] split = "11100110110111000010111111010010111011111111110101100".split("");
        System.out.println(split[23]);
    }

    static void method1(){
        method2();
    }

    static void method2(){
        method3();
    }


    static void method3(){
        log("record log....");
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.stream(stackTraceElements).map(Test::genFullMethod).collect(Collectors.toList()));
    }

    static String genFullMethod(StackTraceElement stackTraceElement){
        return stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName();
    }

    static void log(String info){
        System.out.println("log: "+info);
    }

}
