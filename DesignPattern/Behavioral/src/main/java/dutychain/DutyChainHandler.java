package dutychain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:32
 */
public interface DutyChainHandler {

    void setNextHandler(DutyChainHandler handler);

    void doDutyCall(Param param);

}
