package externalnet;

/**
 * @author vinfer
 * @date 2021-07-14 7:48
 */
public interface ExternalNetPushService {

    /**
     * 推送新录入的房源
     * @param platform  {@link ExternalNetPlatform}
     * @param houseId   房源id
     */
    void pushHouseCreate(ExternalNetPlatform platform, String... houseId);

    /**
     * 推送房源信息更新
     * @param platform  {@link ExternalNetPlatform}
     * @param houseId   房源id
     */
    void pushHouseUpdate(ExternalNetPlatform platform, String... houseId);

    /**
     * 推送房源下架（从中台或前台下架）
     * @param platform  {@link ExternalNetPlatform}
     * @param houseId   房源id
     */
    void pushHouseDelete(ExternalNetPlatform platform, String... houseId);

}
