package stream.parallel;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-10    14:18
 **/
public class ForkJoinTest {


    public static void main(String[] args) {
        //useForkJoin();
        useSingleThread();
    }

    static void useForkJoin(){

        ForkJoinPool pool = new ForkJoinPool();
        /*
         * 使用fork-join计算1到100亿的累加
         * */
        ForkJoinTask<Long> forkJoinTask = new CalculateTask(0, 100000000000L);
        Instant start = Instant.now();
        Long sum = pool.invoke(forkJoinTask);
        Instant end = Instant.now();
        System.out.println(sum);
        //平均花费23s
        System.out.println("共耗时： "+Duration.between(start, end).toMillis()+"ms");
    }



    static void useSingleThread(){
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i < 100000000001L; i++) {
            sum+=i;
        }
        Instant end = Instant.now();
        System.out.println(sum);
        //平均花费28s
        System.out.println("共耗时： "+Duration.between(start, end).toMillis()+"ms");
    }


}
