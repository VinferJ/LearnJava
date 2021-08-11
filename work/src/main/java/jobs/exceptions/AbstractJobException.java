package jobs.exceptions;

/**
 * @author Jiang wenfa
 * @date 2021-08-02 10:18
 */
public abstract class AbstractJobException extends RuntimeException{

    //MY-NOTE 作业执行相关的异常，存在哪些需要抛出异常的点？如果某个操作会抛出异常，经历在接口或抽象类中声明
    //  1. 启动时存在哪些需要报错抛异常的点？
    //  2. 运行时存在哪些需要报错抛异常的点？中断响应？
    //  3. job状态转换间存在哪些需要保存抛异常的点？
    //          运行 -> 挂起/暂停  运行 -> 结束/强制结束（shutdownNow）  当job已在运行时，取消job运行
    //  4.

}
