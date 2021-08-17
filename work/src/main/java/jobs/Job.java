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

    /**
     * 作业被启动之前的初始化方法
     * @param reader            作业的数据读取器，<code>@Nullable</code>
     * @param writer            作业的数据写入器，<code>@Nullable</code>
     * @param processor         作业的逻辑处理器，<code>@NotNull</code>
     */
    void init(GenericDataReader reader,GenericDataWriter writer,JobProcessor processor);

    /**
     * 启动作业
     */
    void start();

    /**
     * 延迟启动作业
     * @param time          延迟时间
     * @param timeUnit      时间单位
     */
    void delayStart(long time, TimeUnit timeUnit);

}
