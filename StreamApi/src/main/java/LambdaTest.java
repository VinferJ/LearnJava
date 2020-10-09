import lambda.MultipleParamsInterface;
import lambda.OneParamInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.Employee;

import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Vinfer
 * @date 2020-10-09    13:56
 * @description
 **/
public class LambdaTest {

    static interface Test<T>{
        T get(String p1,int p2);
    }

    @AllArgsConstructor
    @Data
    static class TwoParams{
        private String param1;
        private int param2;
    }


    public static void main(String[] args) {
        /*
        * lambda表达式根据其箭头函数两侧主要分为两部分：
        *   -> 左侧：方法参数列表
        *   -> 右侧：方法体，lambda表达式主体
        * 语法规则：
        *   1. 当函数为空参时，左侧直接写一对()，当右侧方法体只执行一行代码时，可以省略{}
        *   2. 当只有一个参数时，左侧可以省略()，让右侧方法有返回值时，如果省略{}，那么同时还需要去掉return
        *   3. 当lambda表达式中使用外层方法的局部变量时，该变量默认变为final变量，无法修改值（匿名内部类也一样，并且1.7中必须声明为final）
        * 方法引用规则：
        *   当lambda方法体中的内容以及存在实现了，可以使用方法引用，具体方式为：
        *   1. 对象::实例方法
        *   2. 类::静态方法
        *   3. 类::实例方法
        *   4. 构造器引用：类名::new
        *   5. 数组引用：Type[]::new
        *   使用方法引用时，要求必须是引用的方法的参数列表以及返回值类型都必须和被实现的方法一致
        *
        *
        * */

        Runnable r1 = () -> {
            System.out.println("....r1");
            System.out.println("....r1..again");
        };
        r1.run();

        Runnable r2 = () -> System.out.println(".....r2");
        r2.run();

        OneParamInterface i1 = param -> param+":to test";
        System.out.println(i1.showParam("param-i1"));

        MultipleParamsInterface i2 = (a, b) -> ++a>=--b;
        System.out.println(i2.compare(56, 123));
        System.out.println(i2.compare(445, 12));


        int num = 100;
        /*当这里的num进行++操作时会报错*/
        MultipleParamsInterface i3 = (a, b) -> (a + b) >= num;


        /*
        * 对象::实例方法
        *
        * 最常见的方法引用：System.out::println
        * 下面的lambda表达式等价于：x -> System.out.println(x)
        * */
        Consumer<String> consumer = System.out::println;
        consumer.accept("consumer param...");
        //使用自定义的对象实例
        Supplier<String> supplier = new Employee("vinfer",0,21,999999)::getName;
        System.out.println(supplier.get());

        /*
        * 类::静态方法
        * */
        Comparator<Integer> comparator = Integer::compare;
        /*
        * Integer.compare方法规则：
        *   1.左大于右：返回1
        *   2.左小于右：返回-1
        *   3.左等于右：返回0
        * */
        System.out.println(comparator.compare(9998, 999));

        /*
        * 类::实例方法
        * 当传入的两个参数中，第一参数是引用方法的调用者，第二个参数是引用方法的入参时
        * 可以使用，如：String::equals
        * */
        BiPredicate<String,String> biPredicate = String::equals;
        System.out.println(biPredicate.test("has", "have"));

        /*
        * 构造器引用：被引用的构造器中的参数列表也必须和被实现方法的参数列表一样
        * 实现的方法会通过引用的构造器，构造方法实例，然后成为方法返回值(如果有返回值的话)
        * */
        //supplier中的get是空参的，因此对应引用的Employee中构造器应该是空参构造器
        Supplier<Employee> supplier1 = Employee::new;
        //Test中的get方法对应的是两个参数的参数列表，因此引用的TowParams的构造器也必须是两个参数的构造器
        Test<TwoParams> paramsTest = TwoParams::new;
        System.out.println(supplier1.get());
        System.out.println(paramsTest.get("param1", 222));

        /*数组引用*/
        Function<Integer,String[]> function = String[]::new;
        String[] apply = function.apply(20);
        System.out.println(apply.length);




    }


}
