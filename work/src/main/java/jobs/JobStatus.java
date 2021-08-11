package jobs;

/**
 * @author Jiang wenfa
 * @date 2021-07-31 18:16
 */
public enum JobStatus {


    //MY-NOTE 除了以下的状态，job还可以具备哪些状态？状态可以尽量去细致化一点
    //  为job的所有状态间的转换画出一个状态图，定义出job的状态流

    /**
     * job被创建，尚未启动
     */
    NEW,

    /**
     * job正在执行中
     */
    PROCESSING,

    /**
     * 暂停
     */
    PAUSE,

    /**
     * job正常执行结束
     */
    FINISHED,

    /**
     * job在运行前被取消运行，只有未进入运行状态的job才能转为该状态
     * 被取消运行的job不能被再次执行
     */
    CANCELED,

    /**
     * job未执行结束前已退出执行状态
     */
    EXIT
    ;

}
