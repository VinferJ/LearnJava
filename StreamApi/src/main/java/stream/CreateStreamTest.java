package stream;

import model.Employee;
import utils.ListGenerateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Vinfer
 * @date 2020-10-09    17:07
 * @description
 **/
public class CreateStreamTest {

    public static void createStream(){
        //通过集合创建串行流
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        Stream<Employee> stream1 = employeeList.stream();
        //通过数组创建串行流，Array.stream方法
        String[] arr = new String[10];
        Stream<String> stream2 = Arrays.stream(arr);
        //通过多参/数组/集合创建串行流，使用Stream.of方法
        Stream<String> stream3 = Stream.of("p1", "p2", "p3");
        /*
         * 通过Stream.iterate方法创建无限流（流的长度是无限长的，不终止）
         * 无限流可以通过limit(int size)方法来截取定长的流
         * */
        //1是迭代起点，从1开始不断自增进行无限迭代
        Stream<Integer> stream4 = Stream.iterate(1, num -> num++);

        /*
         * 通过Stream.generate生成无限流
         * */
        Stream<Integer> stream5 = Stream.generate(() -> new Random().nextInt(10000));
    }


}
