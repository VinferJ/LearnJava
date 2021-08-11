package dutychain;

import dutychain.others.Duty1;
import dutychain.others.Duty2;
import dutychain.others.Duty3;
import dutychain.others.ParamDutyChain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:40
 */
public class Client {

    public static void main(String[] args) {
        DutyChainHandler handler1 = new Handler1();
        DutyChainHandler handler2 = new Handler2();
        DutyChainHandler handler3 = new Handler3();
        DutyChainFactory factory = new DutyChainFactory(handler2,handler3,handler1);
        Param param1 = new Param();
        factory.getDutyChain().doDutyCall(param1);
        System.out.println(param1.getVal());


        Param param2 = new Param();
        Duty1 duty1 = new Duty1();
        Duty2 duty2 = new Duty2();
        Duty3 duty3 = new Duty3();
        ParamDutyChain dutyChain = new ParamDutyChain(duty3,duty2,duty1);
        dutyChain.doDuty(param2);
        System.out.println(param2.getVal());
    }

}
