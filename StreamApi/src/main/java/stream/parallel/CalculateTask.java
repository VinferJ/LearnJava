package stream.parallel;


import java.util.concurrent.RecursiveTask;

/**
 * @author Vinfer
 * @date 2020-10-10    14:19
 * @description     fork-join中有两种任务：
 *                      RecursiveTask<V>-带有返回值，使用invoke执行任务，RecursiveAction-没有返回值，使用submit执行任务
 **/
public class CalculateTask extends RecursiveTask<Long> {

    private final long start;
    private final long end;

    private static final long THRESHOLD = 100000;

    public CalculateTask(long start,long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long length = end - start;

        /*
        * 任务拆分算法需要手动实现
        * */
        if ( length > THRESHOLD ){
            /*
             * 如果数据长度大于临界值，进行递归拆分
             * 根据每次数据长度的中间值进行拆分
             * */
            long middle = (end - start) / 2;
            CalculateTask leftTask = new CalculateTask(start, middle);
            //fork子线程，压入线程队列继续拆分或计算
            leftTask.fork();
            CalculateTask rightTask = new CalculateTask(middle + 1, end);
            rightTask.fork();
            return leftTask.join() + rightTask.join();

        }else {
            /*数据长度小于临界值进行累加计算*/
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum+=i;
            }
            return sum;
        }
    }


}
