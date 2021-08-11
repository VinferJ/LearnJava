package dutychain.others;

import dutychain.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiang wenfa
 * @date 2021-07-28 10:27
 */
public class ParamDutyChain implements DutyChain{

    private final List<DutyHandler> dutyHandlerList;

    private int currentPointer;

    public ParamDutyChain(DutyHandler... dutyHandlers){
        dutyHandlerList = new ArrayList<DutyHandler>();
        if (null != dutyHandlers && dutyHandlers.length > 0){
            dutyHandlerList.addAll(Arrays.asList(dutyHandlers));
        }
    }

    public void doDuty(Param param) {
        if (currentPointer < dutyHandlerList.size()){
            dutyHandlerList.get(currentPointer++).doDuty(param,this);
        }
    }

}
