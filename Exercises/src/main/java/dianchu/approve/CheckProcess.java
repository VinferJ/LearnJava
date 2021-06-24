package dianchu.approve;

/**
 * 点触科技2021春招笔试题：审批流程设计
 * 要求：
 *  每个事件的审批流程都拥有多个节点，每个节点都会有一个审批人和几个审批状态：审核中、审核通过、审核驳回
 *  当在某个节点被驳回时，需要流转到上一个节点重新进行审批
 *  在保证审批流程可以正常进行的情况下，还需要具有良好的扩展性
 *
 * @author vinfer
 * @date 2021-03-10 15:28
 */
public abstract class CheckProcess {

    private final ProcessEvent processEvent;
    private ProcessingPoint lastProcessingPoint;
    private ProcessingPoint currentProcessing;
    private final ProcessingChain processingChain;


    public CheckProcess(ProcessEvent processEvent, ProcessingChain processingChain){
        this.processEvent = processEvent;
        currentProcessing = processingChain.getRoot();
        lastProcessingPoint = null;
        this.processingChain = processingChain;
    }

    public void doChecking(int state){
        currentProcessing.doProcessChecking(state);
        if (state == -1){
            if (lastProcessingPoint != null){
                currentProcessing = lastProcessingPoint;
            }else {
                currentProcessing.doProcessChecking(0);
            }
        }else if (state == 1){
            lastProcessingPoint = currentProcessing;
            currentProcessing = processingChain.getNext(currentProcessing);
        }
    }

    public ProcessEvent getProcessEvent(){
        return this.processEvent;
    }


}
