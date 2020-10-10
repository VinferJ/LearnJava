package stream.sequential;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import model.Employee;
import utils.ListGenerateUtil;

import java.util.*;
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
        *   9.collect: 流元素的收集，将流的元素收集并转换成一个集合
        *   9.forEach: 迭代流中所有元素
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
        useCollect(employeeList);
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
        *
        * Optional<T> 对象：jdk8的新特性之一，该对象的设计是为了尽可能地避免空指针异常
        *  -map(Function f):如果有值，那么对其处理并且返回处理后的Optional，否则返回Optional.empty()
        *  -flatMap(Function mapper):与map类似，要求返回值必须是Optional
        *
        * */
        Optional<Employee> any = employeeList.stream().findAny();
        /*
        * orElse: 先进行isPresent判断，如果是true，返回数据，否则返回传入的other对象
        *
        * orElseGet(Supplier<? extend T>other): 与orElse相似，去之前会进行空判断，如果为空，则调用
        *                                       Supplier-other所提供的get方法，orElse则是直接返回other
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
         * 也可以通过collect收集元素后，进行集合的相关操作，得到某个结果（如counting，avg等等）
         * */
        System.out.println("collect: collect name transform to list");
        List<String> nameList = employeeList.stream()
                .map(Employee::getName)
                /*
                * Collectors.toList()等价于Collectors.toCollection(ArrayList::new)
                * 同理Collectors.toSet()等价于Collectors.toCollection(HashSet::new)
                * */
                .collect(Collectors.toList());
        nameList.forEach(System.out::println);

        /*
        * 除了平均值计算，还有总和计算、最值计算等操作
        * 可以直接获取一个对某项值的统计对象，里面就包含了总和、平均、最值等统计数据值
        * */
        System.out.println("collect: sum salary statistics");
        DoubleSummaryStatistics summaryStatistics = employeeList.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(summaryStatistics);

        /*
        * 分组操作：传入单个Function，是一级分组
        * 还可以支持多级（无限级分组），即两个参数类型，第二个参数又是一个Operator
        * 又可以继续在该收集器中继续分组
        * */
        System.out.println("collect: collect all employees group by gender");
        Map<Integer, List<Employee>> empGroup = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender));
        empGroup.forEach((key, value) -> {
            if (key == 0) {
                System.out.println("男员工：" + value);
            } else {
                System.out.println("女员工: " + value);
            }
        });

        System.out.println("collect: multiple group, group by gender and age");
        Map<Integer, Map<String, List<Employee>>> multipleGroup = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(
                        employee -> {
                            if (employee.getAge() <= 20) {
                                return "小鲜肉";
                            } else if (employee.getAge() > 20 && employee.getAge() < 35) {
                                return "小青年";
                            } else {
                                return "中年";
                            }
                        }
                )));
        multipleGroup.forEach((key, value) -> {
            System.out.println(key == 0?"男员工：":"女员工：");
            /*
            * 二级分组中，这里的value仍然是一个map
            * */
            value.forEach((k,v) -> {
                System.out.println(k+": "+v);
            });
        });

        /*
        * 分区操作：满足条件在一个分区，不满足的在一个分区
        * */
        Map<Boolean, List<Employee>> partition = employeeList.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getSalary() > 5000));
        System.out.println("collect: partitioning by salary more then 5000");
        partition.forEach((key,value) -> {
            System.out.println(key?"more then 5000:":"less then 5000:");
            System.out.println(value);
        });

        /*
        * join连接操作，常用的有对字符串的连接操作
        * */
        String collect = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println("collect: collect all name to join");
        System.out.println(collect);
    }

}
