package org.leiers.betterplayerlocations.locationManager;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;
import org.leiers.betterplayerlocations.model.responses.ApiConnectionResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FreeIpApiLocationManager extends AbstractLocationManager {
    private static final String baseUrl = "https://freeipapi.com/api/json/";

    private final Map<UUID, PlayerLocationInformation> cache = new HashMap<>();

    @Override
    public ApiConnectionResponse canConnect() {
        try {
            return performRequest(baseUrl).statusCode() == 200
                    ? ApiConnectionResponse.SUCCESS
                    : ApiConnectionResponse.NOT_AVAILABLE;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerLocationInformation getPlayerInformation(Player player) {
        if (cache.containsKey(player.getUniqueId()))
            return cache.get(player.getUniqueId());

        try {
            final String json = performRequest(baseUrl + getPlayerIp(player)).body();
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(json);
            final String continent = (String) jsonObject.get("continent");
            final String city = (String) jsonObject.get("cityName");
            final String country = (String) jsonObject.get("countryName");

            return new PlayerLocationInformation(continent, country);

        } catch (IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
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
