package stream;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import model.Employee;
import utils.ListGenerateUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    17:16
 * @description
 **/
public class EndStreamTest {


    public static void endStream(){
        System.out.println("流终止操作：");
        /*
        * 流的终止操作有以下几种：
        *   查找与匹配：
        *   1.allMatch: 检查是否匹配所有元素(流中所有元素是否都符合某个添加)
        *   2.anyMatch: 检查是否至少匹配一个元素(流中是否存在至少一个元素满足某个条件)
        *   3.noneMatch: 检查是否没有匹配任何元素(流中是否所有元素都不满足某个条件)
        *   4.findFirst: 返回流中的第一个元素
        *   5.findAny: 返回流中任意一个元素
        *   6.count: 返回流中元素个数总和
        *   7.min: 返回所有流中元素的最小值
        *   8.max: 返回流中元素的最大值
        *
        * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        employeeList.add(new Employee("jack", 0, 21, 12313));
        employeeList.add(new Employee("jack", 1, 56, 12313));
        System.out.println("all emp:");
        employeeList.forEach(System.out::println);
        useAllMatch(employeeList);
        useAnyMatch(employeeList);
        useNoneMatch(employeeList);
        useFindFirst(employeeList);
        useFindAny(employeeList);
        useCount(employeeList);
        useMin(employeeList);
        useMax(employeeList);
    }

    static void useAllMatch(List<Employee> employeeList){
        System.out.println("all match: gender = 1");
        System.out.println(employeeList.stream()
                .allMatch(employee -> employee.getGender() == 1));
    }

    static void useAnyMatch(List<Employee> employeeList){
        System.out.println("any match: name is jack");
        System.out.println(employeeList.stream()
                .anyMatch(employee -> employee.getName().equals("jack")));
    }

    static void useNoneMatch(List<Employee> employeeList){
        System.out.println("none match: age > 100");
        System.out.println(employeeList.stream()
                .noneMatch(employee -> employee.getAge() > 100));
    }

    static void useFindFirst(List<Employee> employeeList){
        System.out.println("find first: ");
        System.out.println(employeeList.stream().findFirst().orElse(null));
    }

    static void useFindAny(List<Employee> employeeList){
        System.out.println("find any: ");
        /*
        * findAny后会返回一个Optional对象
        * 该对象支持对数据的获取、判空、以及过滤等操作
        * */
        Optional<Employee> any = employeeList.stream().findAny();
        /*
        * orElse: 先进行isPresent判断，如果是true，返回数据，否则返回传入的other对象
        * */
        System.out.println(any.orElse(null));
    }

    static void useCount(List<Employee> employeeList){
        System.out.println("count: gender = 0");
        System.out.println(employeeList.stream().filter(employee -> employee.getGender() == 0).count());
    }

    static void useMin(List<Employee> employeeList){
        System.out.println("min: compare by age");
        System.out.println(employeeList.stream().min(Comparator.comparing(Employee::getAge)).orElse(null));
    }

    static void useMax(List<Employee> employeeList){
        System.out.println("max: compare by age");
        System.out.println(employeeList.stream().max(Comparator.comparing(Employee::getAge)).orElse(null));
    }

    static void useCollect(List<Employee> employeeList){
        /*
         * collect在Stream最常用作将原始数据集经过一系列操作后
         * 的流转换成任意类型的集合，如list或set，也可以是其他继承Collection接口的集合
         * */
        Set<Employee> collect = employeeList.stream()
                .filter(employee -> employee.getGender() == 1)
                .collect(Collectors.toSet());
        collect.forEach(System.out::println);



    }

}
