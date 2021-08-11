package jobs;

import java.util.concurrent.TimeUnit;

/**
 * @author Jiang wenfa
 * @date 2021-07-31 19:07
 */
public interface Job {

    //MY-NOTE Job接口该具备什么能力？
    //  1. 初始化和启动是必要的
    //  2. 当前的设计中包含JobExecutor，作业执行器，职责应该就是负责对作业的执行处理
    //      例如作业挂起（暂停）、作业重复执行、作业停止/中断等等
    //      隐藏作业接口本身，是否不应该具备太详细的能力，对于启动以为的能力，应该交给作业执行器去完成
    //  3. JobExecutor是面向多任务执行的组件，如果没有这种场景，job自身也可以启动，但是推荐使用执行器去执行
    //     这种设计理念就想是线程池和线程直接的关系，具体的设计也可以参考两者

    void init(GenericDataReader reader,GenericDataWriter writer,JobProcessor processor);

    void start();

    void delayStart(long time, TimeUnit timeUnit);

    //void stop();

    //void stopNanos(long time);

    //void cancel();

}
