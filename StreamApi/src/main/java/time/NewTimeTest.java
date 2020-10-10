package time;

import java.time.LocalDateTime;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-10    16:06
 * @description
 **/
public class NewTimeTest {


    public static void main(String[] args) {
        /*
        * jdk1.8新的时间日期对象：(ISO-8601标准)
        *       LocalTime   LocalData   LocalDataTime
        * 里面提供大量的有关时间计算及操作的api支持
        *
        * */

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

    }

}
