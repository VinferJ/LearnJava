package externalnet;

import java.util.List;

/**
 * @author vinfer
 * @date 2021-07-14 8:33
 */
public interface ExternalNetServiceStrategy {

    /**
     * 处理房源推送
     * @param teamHouseDocument     {@link TeamHouseDocument}
     */
    void handleHousePush(TeamHouseDocument teamHouseDocument);

    /**
     * 处理通步结果查询
     * @param teamHouseDocument     {@link TeamHouseDocument}
     * @return                      {@link GenericSyncResult}
     */
    List<GenericSyncResult> handleSyncResultQuery(TeamHouseDocument teamHouseDocument);

    /**
     * 获取外网平台信息
     * @return          {@link ExternalNetPlatform}
     */
    ExternalNetPlatform getPlatform();

}
