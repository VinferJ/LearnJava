package dutychain;

import dutychain.others.DutyChain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:34
 */
public class DutyChainFactory {

    private DutyChainHandler dutyChainHandler;

    public DutyChainFactory(DutyChainHandler... dutyChainHandlers){
        if (null != dutyChainHandlers && dutyChainHandlers.length > 0){
            this.dutyChainHandler = dutyChainHandlers[0];
            for (int i = 0; i < dutyChainHandlers.length - 1; i++) {
                dutyChainHandlers[i].setNextHandler(dutyChainHandlers[i+1]);
            }
        }
    }

    public DutyChainHandler getDutyChain(){
        return this.dutyChainHandler;
    }

}
