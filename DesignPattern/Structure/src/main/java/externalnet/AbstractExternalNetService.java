package externalnet;

import java.util.Arrays;

/**
 * @author vinfer
 * @date 2021-07-14 8:08
 */
public abstract class AbstractExternalNetService implements ExternalNetPushService,ExternalNetQueryService{

    public enum PushOperation{
        /**
         * 推送创建
         */
        PUSH_CREATE,
        PUSH_UPDATE,
        PUSH_DELETE
        ;
    }

    @Override
    public void pushHouseCreate(ExternalNetPlatform platform, String... houseId) {
        TeamHouseDocument teamHouseDocument = new TeamHouseDocument();
        validPushHouse(teamHouseDocument);
        validCooperatorInfo();
        Arrays.stream(houseId).forEach(id -> {
            doPushHouseCreate(teamHouseDocument,platform);
        });
        pushSyncStatusToEs(false,teamHouseDocument);
    }

    private void doPushHouseCreate(TeamHouseDocument teamHouseDocument,ExternalNetPlatform platform){
        pushHouseCommunity(teamHouseDocument);
        pushHouseAgentInfo("",teamHouseDocument);
        handleHousePush(teamHouseDocument,platform);
    }

    protected abstract void handleHousePush(TeamHouseDocument teamHouseDocument,ExternalNetPlatform platform);

    @Override
    public void pushHouseUpdate(ExternalNetPlatform platform, String... houseId) {

    }

    @Override
    public void pushHouseDelete(ExternalNetPlatform platform, String... houseId) {

    }

    protected void pushHouseCommunity(TeamHouseDocument teamHouseDocument){};

    protected void pushHouseAgentInfo(String agentId,TeamHouseDocument teamHouseDocument){};

    protected void pushSyncStatusToEs(boolean syncSuccess,TeamHouseDocument teamHouseDocument){

    }

    protected Object genSyncHttpRequest(){ return null; };

    private void validPushHouse(TeamHouseDocument teamHouseDocument){

    }

    private void validCooperatorInfo(){

    }

}
