package dutychain.others;

import dutychain.Param;

/**
 * @author Jiang wenfa
 * @date 2021-07-28 10:31
 */
public class Duty2 implements DutyHandler{
    public void doDuty(Param param, DutyChain dutyChain) {
        param.setVal("-handle by duty2-");
        dutyChain.doDuty(param);
    }
}
