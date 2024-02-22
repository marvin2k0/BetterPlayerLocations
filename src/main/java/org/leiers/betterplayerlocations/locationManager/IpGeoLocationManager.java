package org.leiers.betterplayerlocations.locationManager;

import org.bukkit.entity.Player;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;
import org.leiers.betterplayerlocations.model.responses.ApiConnectionResponse;

import java.util.Map;
import java.util.UUID;

public class IpGeoLocationManager extends AbstractLocationManager {
    @Override
    public ApiConnectionResponse canConnect() {
        return null;
    }

    @Override
    public PlayerLocationInformation getPlayerInformation(Player player) {
        return null;
    }

    @Override
    public Map<UUID, PlayerLocationInformation> getCache() {
        return null;
    }

    @Override
    public String getWebsite() {
        return null;
    }
}
