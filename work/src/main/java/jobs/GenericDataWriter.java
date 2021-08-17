package jobs;

import java.util.List;

/**
 * 通用的数据写入器，数据写入器支持批量写入db <p>
 * 该写入器的写入模式为先将数据暂存到一定大小的数据缓存区，当数据缓冲区被填满后或手动进行冲刷时，数据写入db
 * @author Jiang wenfa
 * @date 2021-07-31 16:46
 */
public interface GenericDataWriter extends JobComponent{

    //MY-NOTE writer真的需要数据缓冲区这个功能吗？这样做目的是是什么？解决了什么问题？
    //        如果单纯只是为了批量保存，传入批量的数据一样可以解决

    /**
     * 批量写入数据并冲刷数据缓冲区
     * @param dataList      要进行写入的数据集合
     */
    <T> void writeAndFlush(List<T> dataList);

    /**
     * 写入单条数据，并冲刷缓存区
     * @param data          要进行写入的数据
     */
    <T> void writeAndFlush(T data);

    /**
     * 向数据缓冲区添加一条数据
     * @param data      要写入的数据
     */
    <T> void appendData(T data);

    /**
     * 冲刷writer的数据缓冲区，将其中的所有数据进行批量写db
     */
    void flushWriter();

    /**
     * 启动writer，writer启动后才能接收并写数据
     */
    void startupWriter();

    /**
     * 关闭writer，writer关闭后，不能再去接收并写数据
     */
    void shutdownWriter();

}
