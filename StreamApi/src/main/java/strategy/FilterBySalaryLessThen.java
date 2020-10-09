package strategy;

import model.Employee;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    10:58
 * @description
 **/
public class FilterBySalaryLessThen implements BaseConditionFilter<Employee> {

    private final float condition;

    public FilterBySalaryLessThen(float salary){
        condition = salary;
    }

    @Override
    public boolean filterByCondition(Employee employee) {
        return employee.getSalary() < condition;
    }
}
