package dutychain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:46
 */
public class Handler2 extends AbstractDutyChainHandler{

    protected void handle(Param param) {
        param.setVal("-handling by handler2");
    }

}
