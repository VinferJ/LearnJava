package externalnet.platform58;

import externalnet.ExternalNetPlatform;
import externalnet.ExternalNetServiceStrategy;
import externalnet.GenericSyncResult;
import externalnet.TeamHouseDocument;

import java.util.List;

/**
 * @author vinfer
 * @date 2021-07-14 8:38
 */
public class Ajk58ExternalServiceStrategy implements ExternalNetServiceStrategy {
    @Override
    public void handleHousePush(TeamHouseDocument teamHouseDocument) {

    }

    @Override
    public List<GenericSyncResult> handleSyncResultQuery(TeamHouseDocument teamHouseDocument) {
        return null;
    }

    @Override
    public ExternalNetPlatform getPlatform() {
        return null;
    }
}
