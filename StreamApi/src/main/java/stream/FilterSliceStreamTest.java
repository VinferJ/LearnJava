package stream;

import model.Employee;
import utils.ListGenerateUtil;

import java.util.List;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    17:12
 * @description
 **/
public class FilterSliceStreamTest {

    public static void filterAndSliceStream(){
        System.out.println("流的中间操作：筛选和分片");
        /*
         * 流的中间操作：
         *   筛选与切片：
         *       1.filter: 条件过滤，可接收lambda表达式
         *       2.limit(n): 从第n+1个元素开始截断流（尾部截断），使流中的元素不超过n个,如果流中的元素个数小于n，则返回一个空的流
         *       3.skip(n): 跳过元素，返回一个扔掉了前n个元素的流，即首元素开始一直截断到第n个元素（头部截断），与limit互补
         *       4.distinct: 元素去重，通过流所生成的元素的hashCode() 和 equals() 去除重复元素
         * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        useFilter(employeeList);
        useLimit(employeeList);
        useSkip(employeeList);
        Employee jack = new Employee("jack", 0, 23, 123132);
        employeeList.add(jack);
        employeeList.add(jack);
        useDistinct(employeeList);

    }

    static void useFilter(List<Employee> employeeList){
        System.out.println("filter: ");
        /*
         * 使用stream过滤，
         * 通过过滤的顺序可以实现多层过滤，类似责任链模式
         * 相当于sql语句中多个限定条件的筛选
         * */
        employeeList.stream()
                .filter((employee -> employee.getAge()>35))
                .filter(employee -> employee.getSalary() >= 2000)
                .filter(employee -> employee.getGender() == 0)
                .forEach(System.out::println);
    }

    static void useLimit(List<Employee> employeeList){
        System.out.println("limit: ");
        employeeList.stream()
                .limit(5)
                .forEach(System.out::println);
    }

    static void useSkip(List<Employee> employeeList){
        System.out.println("skip: ");
        employeeList.stream()
                .skip(5)
                .forEach(System.out::println);
    }

    static void useDistinct(List<Employee> employeeList){
        System.out.println("distinct: ");
        employeeList.stream()
                .distinct()
                .forEach(System.out::println);
    }

}
