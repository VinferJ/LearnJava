package strategy;

import model.Employee;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    11:07
 * @description
 **/
public class FilterNameByGender implements BaseConditionFilter<Employee> {

    private final int condition;

    public FilterNameByGender(int gender){
        condition = gender;
    }

    @Override
    public boolean filterByCondition(Employee employee) {
        return employee.getGender() == condition;
    }

    public String filterNameByCondition(Employee employee){
        return filterByCondition(employee)?employee.getName():null;
    }



}
