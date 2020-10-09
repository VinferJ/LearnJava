import model.Employee;
import strategy.BaseConditionFilter;
import strategy.FilterByAgeMoreThen;
import strategy.FilterBySalaryLessThen;
import strategy.FilterNameByGender;
import utils.ListGenerateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinfer
 * @date 2020-10-09    11:01
 * @description
 **/
public class StrategyTest {

    public static void main(String[] args) {
        /*
        * 使用策略模式优化后，方法冗余明显降低
        * 但是对于过滤数据后，需要单独取出数据的需求，仍旧需要单独定义方法
        *
        * 因此最终优化的方式就出来了：使用Stream都可以搞定.....
        * */
        List<Employee> employeeList = ListGenerateUtil.generateEmpList(20);
        System.out.println(filterByCondition(employeeList, new FilterByAgeMoreThen(30)));
        System.out.println(filterByCondition(employeeList, new FilterBySalaryLessThen(3200)));
        System.out.println(filterNameByCondition(employeeList, new FilterNameByGender(1)));
        System.out.println("===================================use inner class====================");
        /*
        * 使用匿名内部类
        * */
        System.out.println(filterByCondition(employeeList,new BaseConditionFilter<Employee>(){
            @Override
            public boolean filterByCondition(Employee employee) {
                return employee.getAge() <= 20;
            }
        }));
        System.out.println("===================================use lambda expression====================");
        /*
        * 可以看出，使用lambda表达式后，代码的简洁程度变得更高，可读性也提高了
        * 但是！！！lambda表达式的使用也有局限，前提必须是，接口有且只有一个方法，拥有多个方法的接口，不适用
        *
        * 函数式接口：接口中有且只有一个抽象方法
        * */
        System.out.println(filterByCondition(employeeList,((employee) -> employee.getSalary() >= 5000)));

    }


    public static List<Employee> filterByCondition(List<Employee> employeeList,BaseConditionFilter<Employee> conditionFilter){
        /*
        * 通过使用策略模式，对过滤方法可以进行抽取优化为一个方法
        * 不同的策略拥有独立实现，实现松耦
        * */
        List<Employee> filterList = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (conditionFilter.filterByCondition(employee)){
                filterList.add(employee);
            }
        }
        return filterList;
    }

    public static List<String> filterNameByCondition(List<Employee> employeeList,FilterNameByGender conditionFilter){
        List<String> filterList = new ArrayList<>();
        for (Employee employee : employeeList) {
            String s = conditionFilter.filterNameByCondition(employee);
            if (s != null){
                filterList.add(s);
            }
        }
        return filterList;
    }



}
