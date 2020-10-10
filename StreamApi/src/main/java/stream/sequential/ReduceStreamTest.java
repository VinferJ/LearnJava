package stream.sequential;

import model.Employee;
import utils.ListGenerateUtil;

import java.util.List;
import java.util.Optional;

/**
 * @author Vinfer
 * @date 2020-10-09    17:41
 * @description
 **/
public class ReduceStreamTest {


    public static void reduceStream(){
        System.out.println("流的归约操作：");
        /*
        * 流的归约操作：可以将流中的元素反复结合/操作/运算,得到一个值，也属于流的终止操作
        *   reduce(T identity, BinaryOperator) : 带有起始值identity，该值作为第一次入参的左边的参数
        *                                        返回值类型与传入的T类型一致，并且返回一定不为空，因为有起始值identity
        *   reduce(BinaryOperator)  : 无初始值，返回类型是Optional，其中包含的数据/结果有可能为空
        *
        * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        useReduce(employeeList);
    }

    static void useReduce(List<Employee> employeeList){
        System.out.println("reduce: sum all employees age");
        /*
        * 通过map提取员工年龄后再用reduce计算男性员工年龄总和
        * 通常map和reduce的组成通常称为map-reduce/MapReduce模式，
        * 是大数据中常用的操作，在Hadoop中就有该模式
        * */
        Integer ageSum = employeeList.stream()
                .map(Employee::getAge)
                /*
                * Integer::sum 等价于 (x,y)-> x+y
                * identity作为第一次入参的x，第一次入参的y则是流中的第一个元素
                * 而第二次运算时，上一次计算得到的结果作为x，流的下一个元素作为y，继续运算
                * 知道流中所有元素都经过运算，最终得到结果
                * */
                .reduce(0, Integer::sum);
        System.out.println(ageSum);
        System.out.println("reduce: sum all employees to one");
        /*
        * 不带入参的reduce，这里通过拼接所有员工的姓名，累加所有员工的年龄和工资
        * 得到一个新Employee
        * */
        Optional<Employee> reduceEmp = employeeList.stream()
                .reduce((emp1, emp2) -> {
                    int age = emp1.getAge() + emp2.getAge();
                    float salary = emp1.getSalary() + emp2.getSalary();
                    String name = emp1.getName() + emp2.getName();
                    return new Employee(name, emp1.getGender(), age, salary);
                });
        System.out.println(reduceEmp.orElse(null));
    }


}
