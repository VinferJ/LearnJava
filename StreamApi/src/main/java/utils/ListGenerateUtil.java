package utils;

import model.Employee;
import model.ParentChild;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    17:10
 * @description
 **/
public class ListGenerateUtil {

    public static List<Employee> generateEmpList(int size){
        List<Employee> employeeList = new ArrayList<Employee>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            employeeList.add(new Employee("emp"+i, random.nextInt(10)>=5?0:1, random.nextInt(50), random.nextFloat()*10000 ));
        }
        return employeeList;
    }

    public static List<ParentChild> generateParentChildList(){
        List<ParentChild> parentChildList = new ArrayList<>();
        parentChildList.add(new ParentChild(1, 5));
        parentChildList.add(new ParentChild(1, 6));
        parentChildList.add(new ParentChild(3, 8));
        parentChildList.add(new ParentChild(3, 9));
        parentChildList.add(new ParentChild(3, 10));
        parentChildList.add(new ParentChild(4, 12));
        parentChildList.add(new ParentChild(6, 7));
        parentChildList.add(new ParentChild(10, 11));
        parentChildList.add(new ParentChild(12, 13));
        parentChildList.add(new ParentChild(12, 14));
        parentChildList.add(new ParentChild(13, 15));
        parentChildList.add(new ParentChild(15, 16));
        parentChildList.add(new ParentChild(16, 17));
        parentChildList.add(new ParentChild(2, 18));
        return parentChildList;
    }

}
