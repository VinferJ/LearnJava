package jobs;

/**
 * 组成一个job的基础组件的组件类型枚举
 * @author Jiang wenfa
 * @date 2021-08-02 11:29
 */
public enum JobComponentType {

    /**
     * reader组件：{@link GenericDataReader}
     */
    READER,

    /**
     * 处理器组件：{@link JobProcessor}
     */
    PROCESSOR,

    /**
     * writer: {@link GenericDataWriter}
     */
    WRITER
    ;

}
