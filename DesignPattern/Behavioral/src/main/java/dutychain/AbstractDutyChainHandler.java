package dutychain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:41
 */
public abstract class AbstractDutyChainHandler implements DutyChainHandler{

    private DutyChainHandler nextHandler;

    public void setNextHandler(DutyChainHandler nextHandler) {
        //MY-NOTE
        // 通过setNext的指针引用成链是责任链中常用的方式，由于
        // 除了指针成链以为，还可以通过将handler放到一个有序集合中进行成链
        // 责任链成链时，一般都可以进行排序，上述两种方式都能够支持排序，比较常见的排序支持是通过定义一个Order注解去定义顺序
        this.nextHandler = nextHandler;
    }

    public void doDutyCall(Param param) {
        // 抽象父类可以做策略控制，或者是公共的流程控制
        if (param.getVal() == null || !param.getVal().contains("3")){
            handle(param);
        }
        // 不是当前责任链的职责，传递给下一个handler去执行
        if (nextHandler != null){
            nextHandler.doDutyCall(param);
        }
    }

    /**
     * 具体的职责执行交给子类，子类只需要关心执行职责
     * @param param         参数
     */
    protected abstract void handle(Param param);
}
