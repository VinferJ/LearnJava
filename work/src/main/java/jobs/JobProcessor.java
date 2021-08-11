package jobs;

/**
 * 执行作业业务逻辑的处理器
 * @author Jiang wenfa
 * @date 2021-07-31 18:11
 */
public interface JobProcessor extends JobComponent{

    /**
     * 处理作业
     * @param reader        {@link GenericDataReader}
     * @param writer        {@link GenericDataWriter}
     */
    void processJob(GenericDataReader reader, GenericDataWriter writer);

    void processJob(GenericDataReader reader);

    void processJob(GenericDataWriter writer);

    void processJob();

    //MY-NOTE
    //  作业的处理需要将reader和writer传入，但是这只是提供了这种设计
    //  并不是说没有reader和writer就不能进行作业处理了
    //  所以当前的作业处理器拥有4种作业处理方法，同时也是对应了不同的作业处理场景
    //  所以基于单一职责以及迪米特法则，也需要再去考虑一下作业处理器的接口应该如何设计？
    //      是4个处理方法柔和在一个接口中？
    //      还是将4种应对不同场景的的作业处理分成4个不同的处理器接口?
    //          如果是将接口做分开设计，那也必须要有一个公共接口来作为作业处理器的接口，
    //          该作业处理器的父接口让Job去依赖，而参数可以传入不同的子接口，
    //          然后通过一些策略判断到底是哪种处理场景，然后通过接口强制去调用对应的子处理器的处理方法

}
