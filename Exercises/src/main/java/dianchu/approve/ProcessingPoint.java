package dianchu.approve;

/**
 * 流程处理节点
 *
 * @author vinfer
 * @date 2021-03-10 15:33
 */
public abstract class ProcessingPoint {

    private static final int REJECT = -1;
    private static final int CHECKING = 1;
    private static final int APPROVE = 2;

    /**当前节点处理人*/
    private Approver approver;

    private int state;

    public ProcessingPoint(Approver approver,int state){
        this.approver = approver;
        this.state = state;
    }

    public ProcessingPoint(Approver approver){
        this(approver,CHECKING);
    }

    public void doProcessChecking(int state){

    }

    private void reject(){
        this.state = REJECT;
    }

    private void approve(){
        this.state = APPROVE;
    }

    public void changeApprover(Approver approver){
        this.approver = approver;
    }

}
