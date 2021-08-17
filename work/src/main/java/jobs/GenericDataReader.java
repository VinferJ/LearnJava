package jobs;

import java.util.List;

/**
 * 通用的数据读取器，在启动reader后，可以通过reader可以读取任意大小的数据集
 * @author Jiang wenfa
 * @date 2021-07-31 16:32
 */
public interface GenericDataReader extends JobComponent{

    /**
     * 批量读取数据，当reader中剩余数据量小于limit时，不会严格返回指定大小的数据集，
     * @param limit                 批量数据上限数
     * @return                      批量数据集，数据集大小小于等于limit
     */
    <T> List<T> readBatch(int limit);

    /**
     * 批量读取数据，严格返回指定大小的数据集，当reader中数据量不足返回指定大小的批量数据时，<p>
     * 会阻塞读取数据的线程，直到生成了大小等于limit的数据集
     * @param limit                 批量数据上限数
     * @param strictToLimitNum      是否严格读取指定数量的批量数据
     * @return                      批量数据集，数据集大小一定等于limit
     */
    <T> List<T> readBatch(int limit,boolean strictToLimitNum);

    /**
     * 读取1条数据
     * @return      数据对象
     */
    //<T> T readOne();

    /**
     * reader中是否还存在可以被读取的数据
     * @return      reader存在可读取数据，返回true，否则返回false
     */
    boolean hasMoreData();

    /**
     * 启动reader，让reader开始去生产数据以供读取
     */
    void startUpReader();

    /**
     * 关闭reader，reader被关闭后，无法再读取数据
     */
    //void shutdownReader();

}
