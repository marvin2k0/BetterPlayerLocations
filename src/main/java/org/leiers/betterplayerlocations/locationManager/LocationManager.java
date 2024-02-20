package org.leiers.betterplayerlocations.locationManager;

import org.bukkit.entity.Player;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;
import org.leiers.betterplayerlocations.model.responses.ApiConnectionResponse;

import java.util.Map;
import java.util.UUID;

public interface LocationManager {
    ApiConnectionResponse canConnect();

    PlayerLocationInformation getPlayerInformation(Player player);

    Map<UUID, PlayerLocationInformation> getCache();

    String getWebsite();
}
