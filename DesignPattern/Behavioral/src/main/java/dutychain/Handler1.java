package dutychain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:40
 */
public class Handler1 extends AbstractDutyChainHandler{

    protected void handle(Param param) {
        param.setVal("-handling by handler1");
    }
}
