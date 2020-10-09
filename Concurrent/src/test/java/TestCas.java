import cas.CasOperations;
import cas.CasThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vinfer
 * @date 2020-08-23    13:25
 **/
public class TestCas {

    public static void main(String[] args) {

        CasOperations casInt = new CasOperations(1);
        System.out.println(casInt.casGetAndAdd());
        System.out.println(casInt.getVal());
        /*
        * 当casInt实例的val为传入的期望值时进行值更新，返回的是true
        * */
        System.out.println(casInt.compareAndSet(casInt.getVal(), casInt.getVal()+1));
        /*
        * 只要casInt的val不满足当前传入的期望值，那么会修改失败，直接返回false
        * */
        System.out.println(casInt.compareAndSet(10, 100));

        /*
        * 并发场景下测试
        * */
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new CasThread(casInt, "thread-"+i));
        }

    }

}
