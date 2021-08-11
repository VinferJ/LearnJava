package dutychain.others;

import dutychain.Param;

/**
 * @author Jiang wenfa
 * @date 2021-07-28 10:30
 */
public class Duty1 implements DutyHandler{
    public void doDuty(Param param, DutyChain dutyChain) {
        param.setVal("-handle by duty1-");
        dutyChain.doDuty(param);
    }
}
