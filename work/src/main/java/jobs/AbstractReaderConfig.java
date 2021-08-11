package jobs;

/**
 * @author Jiang wenfa
 * @date 2021-08-02 09:18
 */
public abstract class AbstractReaderConfig {


    //MY-NOTE reader初始化配置：
    // 1. reader如何去生产数据？
    //      1.1 读db -> 需要dao对象，sql，dao执行sql
    //      1.2 网络io-http或rpc -> 需要httpClient或rpcClient，url，请求参数
    //      1.3 本地io -> 需要用于数据读取的文件的路径，IOStream
    //      本质上是生成者去生成数据，可以考虑使用Supplier<T>，reader通过执行supplier.get的计算拿到数据，
    //      如果让外部提供Supplier<T>，那就意味着使用者需要完全自己去实现数据生产的业务逻辑，
    //      当前reader作为数据流的一个中间层，提供暂存机制以及对外提供消费的入口
    //      但是需要注意的一个点：当db需要循环读取某个业务的全部数据时，Supplier<T>要如何支持？
    //                          让reader去循环调用get方法？如何停下来？使用者如何知道这些规则？
    //      因此对于数据生产的方式，还必须要去抽象定义一个基类去封装基本的流程，把具体的数据生产的实现留给使用者去写子类去完成
    // 2. reader的运行参数配置：
    //      2.1 内部用于缓存数据的阻塞队列大小（慎用无界队列，因为如果处理器消费速度跟不上会导致oom）
    //      2.2 异步执行的线程池配置，默认使用SingleThread的线程池

}
