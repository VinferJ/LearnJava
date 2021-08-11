package jobs;

/**
 * 作业监听器，用于监听作业执行的某个阶段或某些状态
 * @author Jiang wenfa
 * @date 2021-08-02 10:41
 */
public interface JobListener {

    //MY-NOTE job的监听器应该如何设计？参照观察者模式，参照spring的监听器的设计
    //  tip: 对于job来说，通用的监听应该是job启动、job执行成功以及job执行失败等场景去做监听
    //       监听的目的主要是为了执行一些场景完成后的后置操作，并且监听本身也是一种解耦设计，可以为框架提供一定扩展性
    //       需要结合实际问题去考虑设计，不要过度设计，因为监听器并不是该框架的一个核心点
    //  1. 监听方法的参数应该传什么？spring基于事件监听，传的是事件对象，是否适合当前框架
    //  2. 监听对象应该如何去注册？如果基于事件去做监听，那么可以在监听对象和监听器之间进行解耦，但是设计较复杂

    //MY-NOTE 作业执行的容错能力可以考虑通过监听器实现

    void onJobStarted();

    void onJobPaused();

    void onJobExecSucceed();

    void onJobExecFail();

}
