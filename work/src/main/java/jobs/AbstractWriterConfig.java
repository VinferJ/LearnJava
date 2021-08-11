package jobs;

/**
 * @author Jiang wenfa
 * @date 2021-08-02 09:19
 */
public abstract class AbstractWriterConfig {

    //MY-NOTE writer如何去写数据？
    // 1. 数据最终持久化到哪里？
    //      1.1 持久化到db -> 需要dao对象，sql，dao执行写入数据的sql
    //      1.2 本地io -> 需要用于保存数据的文件的路径
    //      1.3 网络io-http或rpc -> 需要httpClient或rpcClient，url，请求参数
    //      本质上是一个消费者在消费数据，可以考虑使用一个Consumer<T>去做消息消费，
    //      同样并不能直接让外部提供一个Consumer<T>，也应该去考虑定义出一个抽象类，
    //      提供数据消费的核心流程，把最终的持久化逻辑作为抽象方法，让使用者去写子类去实现
    // 2. writer的运行配置：
    //    tip: writer接口中已经提供了数据缓冲的接口方法，但是需要去考虑一下这样做的目的，是否真的有必要这样做？
    //         或者提供数据缓冲的功能，但是可以通过配置来决定是否使用
    //      2.1 writer的运行线程池的配置：默认使用SingleThread的线程池
    //      2.2 数据缓冲区大小（当append到数据缓冲区的数据量达到该阈值后，自动冲刷进行持久化）

}
