package strategy;

import model.Employee;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    10:56
 * @description
 **/
public class FilterByAgeMoreThen implements BaseConditionFilter<Employee> {

    private final int condition;

    public FilterByAgeMoreThen(int age){
        condition = age;
    }

    @Override
    public boolean filterByCondition(Employee employee) {
        return employee.getAge() > condition;
    }
}
