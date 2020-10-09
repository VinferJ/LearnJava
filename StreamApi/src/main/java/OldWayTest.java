import model.Employee;
import utils.ListGenerateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Vinfer
 * @date 2020-10-09    10:23
 * @description
 **/
public class OldWayTest {

    public static void main(String[] args) {
        /*
        * 对于数据的过滤，传统的实习方式存在的弊端就是代码冗余
        * 每当有一个新的需求出现，就需要有对应的方法实现
        * 并且方法都大体相似，都是对数据中某一项进行判断后进行过滤
        * 因此可以存在以下几种优化：
        *   优化方式1：使用策略模式，提供一个顶层的filter接口，通过不同实现类实现不同的过滤策略
        *   优化方式2：基于1中的策略模式，对于简单策略使用匿名内部类，不在提供单独的实现类
        *   优化方式3：基于2，使用lambda表达式代替匿名内部类
        *   优化方式4：使用Stream API
        *
        * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(20);
        System.out.println(filterByAgeMoreThen(employeeList, 30));
        System.out.println(filterBySalaryLessThen(employeeList, 5000));
        /*
        * 使用lambda的forEach输出
        * */
        filterNameByGender(employeeList, 0).forEach(System.out::println);
    }

    public static List<Employee> filterByAgeMoreThen(List<Employee> employeeList,int age){
        /*
        * 需求1：需要过滤年龄大于界定值的员工
        * 传统实现：定义一个集合，通过条件匹配，将过滤的元素放到集合中并返回
        * */
        List<Employee> filterList = new ArrayList<Employee>(employeeList.size());
        for (Employee employee : employeeList) {
            if (employee.getAge() > age){
                filterList.add(employee);
            }
        }
        return filterList;
    }

    public static List<Employee> filterBySalaryLessThen(List<Employee> employeeList,float salary){
        /*
        * 需求2：需要过滤薪资低于界定值的员工
        * */
        ArrayList<Employee> filterList = new ArrayList<Employee>(employeeList.size());
        for (Employee employee : employeeList) {
            if (employee.getSalary() < salary){
                filterList.add(employee);
            }
        }
        return filterList;
    }

    public static List<String> filterNameByGender(List<Employee> employeeList,int gender){
        /*
        * 需求：过滤指定性别的员工的名字
        * */
        ArrayList<String> filterList = new ArrayList<String>(employeeList.size());
        for (Employee employee : employeeList) {
            if (employee.getGender() == gender){
                filterList.add(employee.getName());
            }
        }
        return filterList;
    }


}
