package stream;

import model.Employee;
import utils.ListGenerateUtil;

import java.util.Comparator;
import java.util.List;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    17:15
 * @description
 **/
public class SortStreamTest {

    public static void sortStream(){
        System.out.println("流的中间操作：排序");
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(10);
        employeeList.add(new Employee("jackson", 0, 23, 121221));
        employeeList.add(new Employee("john", 0, 23, 121221));
        employeeList.add(new Employee("catalina", 1, 23, 121221));
        useSorted(employeeList);
    }

    static void useSorted(List<Employee> employeeList){
        /*
         * Comparator.comparing(param)
         * 如果传入的是String，会使用类似字典排序的比较方式
         * */
        System.out.println("sorted by age：");
        employeeList.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .forEach(System.out::println);

        /*
         * 自定义比较方法
         * */
        System.out.println("sorted by name and age: ");
        employeeList.stream()
                .sorted((e1,e2) -> {
                    if (e1.getAge() == e2.getAge()){
                        return e1.getName().compareTo(e2.getName());
                    }else {
                        return Integer.compare(e1.getAge(), e2.getAge());
                    }
                })
                .forEach(System.out::println);

    }

}
