package externalnet;

import java.util.List;

/**
 * @author vinfer
 * @date 2021-07-14 7:49
 */
public interface ExternalNetQueryService {

    /**
     * 获取房源的同步结果
     * @param platform      {@link ExternalNetPlatform}
     * @param houseId       房源id
     * @return              {@link GenericSyncResult}
     */
    List<GenericSyncResult> getSyncResult(ExternalNetPlatform platform, String houseId);

}
