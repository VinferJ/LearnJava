package jobs;

import java.util.Collection;
import java.util.List;

/**
 * 作业调度器，负责对具体的job的执行、挂起(暂停)、中断、取消等操作
 * @author Jiang wenfa
 * @date 2021-07-31 19:20
 */
public interface JobExecutor {

    //MY-NOTE
    //  JobExecutor是整个框架的一个核心点，需要着重考虑该组件的设计和实现
    //  接口定义了该组件能够拥有什么能力，一定要考虑清楚
    //  除此之外，还应该去写一个抽象类实现该接口，对接口中核心的功能中的流程做完善的控制，并且将流程中的公共处理抽象出来
    //  该抽象类如何编码是重中之重，并且还要考虑清楚抽象类的子类实现具体需要负责做什么？这样可以有助于划分父类和子类的职责界限
    //  JobExecutor可以参考线程池的设计
    //  JobExecutor中对Job的查找应该是需要依赖于JobContext的，两者的设计应该是同时存在或不存在的

    //MY-NOTE 思考清楚职责！！！！
    //  当前接口中定义了不少任务启动和停止相关的方法，但是调度器的语义和核心在于调度！！！先去搞清楚什么叫调度！！！
    //  调度器的职责可以去参考OS的线程调度又或者是作业调度，作业调度的作用应该更强调对作业状态间转换的作业
    //  搞清楚之后，再考虑当前的框架是否需要有作业调度器的这个角色或组件
    //  当前接口中设计的方法，更像是一个执行器该拥有的能力，接口该叫JobExecutor

    void startJob(Job job);

    void batchStart(Collection<Job> jobs);

    void pauseJob(long jobId);

    void batchPause(List<Long> jobIdList);

    void shutdown(long jobId);

    void shutdownNow(long jobId);

    void cancel(long jobId);

}
