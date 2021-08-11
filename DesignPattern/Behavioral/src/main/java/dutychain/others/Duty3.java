package dutychain.others;

import dutychain.Param;

/**
 * @author Jiang wenfa
 * @date 2021-07-28 10:31
 */
public class Duty3 implements DutyHandler{
    public void doDuty(Param param, DutyChain dutyChain) {
        param.setVal("-handle by duty3-");
        dutyChain.doDuty(param);
    }
}
