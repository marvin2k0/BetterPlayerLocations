package org.leiers.betterplayerlocations.locationManager;

import org.bukkit.entity.Player;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;
import org.leiers.betterplayerlocations.model.responses.ApiConnectionResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreeIpApiLocationManager extends AbstractLocationManager {
    private static final String baseUrl = "https://freeipapi.com/api/json/";

    private final Map<UUID, PlayerLocationInformation> cache = new HashMap<>();

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
        return cache;
    }

    @Override
    public String getWebsite() {
        return "freeipapi.com";
    }
}
