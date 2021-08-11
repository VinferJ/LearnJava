package externalnet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vinfer
 * @date 2021-07-14 8:19
 */
public class ExternalNetServiceStrategiesManager extends AbstractExternalNetService{

    private final Map<ExternalNetPlatform,ExternalNetServiceStrategy> serviceStrategiesMap;

    public ExternalNetServiceStrategiesManager(Map<ExternalNetPlatform,ExternalNetServiceStrategy> serviceStrategiesMap){
        if (null == serviceStrategiesMap){
            throw new RuntimeException("external net service strategy map is null");
        }
        this.serviceStrategiesMap = serviceStrategiesMap;
    }

    @Override
    protected void handleHousePush(TeamHouseDocument teamHouseDocument,ExternalNetPlatform platform) {
        ExternalNetServiceStrategy serviceStrategy = serviceStrategiesMap.get(platform);
        validServiceStrategy(serviceStrategy,platform);
        //MY-NOTE 只负责外网房源推送请求的发起，manager通过获取推送的类型，传入对应的推送url
        serviceStrategy.handleHousePush(teamHouseDocument);
    }

    @Override
    public List<GenericSyncResult> getSyncResult(ExternalNetPlatform platform, String houseId) {
        ExternalNetServiceStrategy serviceStrategy = serviceStrategiesMap.get(platform);
        validServiceStrategy(serviceStrategy,platform);
        return serviceStrategy.handleSyncResultQuery(new TeamHouseDocument());
    }

    private void validServiceStrategy(ExternalNetServiceStrategy serviceStrategy,ExternalNetPlatform platform){
        if (null == serviceStrategy){
            throw new RuntimeException("No external net service strategy match for platform ["+platform+"]");
        }
    }

}
