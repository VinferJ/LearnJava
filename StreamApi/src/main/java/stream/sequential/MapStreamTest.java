package stream.sequential;

import model.Employee;
import utils.ListGenerateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    17:14
 * @description
 **/
public class MapStreamTest {

    public static void mapStream(){
        System.out.println("流的中间操作：映射");
        /*
         * 映射操作：
         *   1.map: 接收lambda表达式，将元素转换成其他形式或者是提取信息，或者接受一个函数作为参数
         *         该函数会作用到每个元素上，并且将其映射成一个新元素
         *   2.flatMap: 接收一个函数作为参数，将流中的每一元素都换成另外一个流，然后再把所有由元素转换成的流都连接成一个流
         *
         * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        useMap(employeeList);
        useFlatMap(employeeList);
    }

    static void useMap(List<Employee> employeeList){
        /*
         * stream中map的功能主要是对数据流中某项属性的抽取和映射
         * 比如抽取出所有员工的姓名、抽取出符合某条件的员工的姓名
         * */
        System.out.println("all employees name: ");
        employeeList.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        System.out.println("filtered employees name: ");
        employeeList.stream()
                .filter((employee -> employee.getAge()>35))
                .filter(employee -> employee.getSalary() >= 2000)
                .filter(employee -> employee.getGender() == 0)
                .map(Employee::getName)
                .forEach(System.out::println);
        /*
         * 通过map只能抽取POJO中某个属性，如果需要生成过滤后
         * 数据的多个属性的子集，可以在forEach中进行封装
         * */
        System.out.println("年龄35以上，月薪大于2000的员工有：");
        employeeList.stream()
                .filter((employee -> employee.getAge()>35))
                .filter(employee -> employee.getSalary() >= 2000)
                .forEach(employee -> {
                    String name = employee.getName();
                    int gender = employee.getGender();
                    /*
                     * 在这里封装任意抽取的属性集合
                     * */
                    System.out.println("name: "+name+", gender: "+(gender==0?"男":"女"));
                });
        /*
         * 将string集合中所有的元素都转换成大写
         * */
        System.out.println("小写转换为大写：");
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    static void useFlatMap(List<Employee> employeeList){
        /*
         * flatMap: 将所有元素变成的流汇再聚成一个流，与map的区别就好比是：
         *          add(Object obj)方法和addAll(Collection coll)方法
         *          add如果传入一个集合，那么整个集合就是一个元素（对应了map，整个list就是一个流）
         *          addAll如果传入一个集合，那么会将传入的集合中的所有元素都添加到集合和中成为独立的元素
         *          (对应了flatMap，一个流的所有元素都变成单一的流，最后再变成一个流)
         * */
        employeeList.stream()
                .flatMap(MapStreamTest::generateEmpEleStream)
                .forEach(System.out::println);
    }

    static Stream<Object> generateEmpEleStream(Employee employee){
        /*
         * 把每个employee中所有的属性都拿出来放到一个集合中
         * 并且把这个集合转换成流返回
         * 而flatMap的作用就是将这些通过传入元素而转换成的流连接成一个流
         * */
        List<Object> employeeEle = new ArrayList<>();
        employeeEle.add(employee.getName());
        employeeEle.add(employee.getAge());
        employeeEle.add(employee.getGender());
        employeeEle.add(employee.getSalary());
        return employeeEle.stream();
    }

}
