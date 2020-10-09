package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Vinfer
 * @date 2020-10-09    10:15
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private String name;
    private int gender;
    private int age;
    private float salary;


}
