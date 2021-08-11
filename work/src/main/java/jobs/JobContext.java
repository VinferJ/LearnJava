package jobs;

/**
 * @author Jiang wenfa
 * @date 2021-08-02 10:58
 */
public interface JobContext {

    //MY-NOTE
    //  job的上下文接口，主要是对外提供job数据的获取，具体的设计可以参考spring的BeanFactory以及ApplicationContext
    //  使用JobContext，那么需要去考虑清楚，job到底是怎么生成的怎么来的，如果仅仅就是手动的去构建job然后就立刻执行的这种场景，
    //  那么很明显不需要JobContext，jobContext应该是面向多任务执行时，对任务数据的保存，如果当前场景下并没有多任务或批量任务执行的需求
    //  那么不需要设计JobContext，也不需要JobScheduler了，应该作业调度器必定需要依赖于JobContext去获取任务

}
