package org.leiers.betterplayerlocations.locationManager;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;
import org.leiers.betterplayerlocations.model.responses.ApiConnectionResponse;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IpStackLocationManager extends AbstractLocationManager {
    private final Map<UUID, PlayerLocationInformation> cache;
    private final String checkUrl;
    private final String apiUrl;

    public IpStackLocationManager(String apiKey) {
        this.cache = new HashMap<>();
        this.checkUrl = "http://api.ipstack.com/check?access_key=" + apiKey + "&fields=country_name";
        this.apiUrl = "http://api.ipstack.com/%s?access_key=" + apiKey + "&fields=country_name,continent_name";
    }

    public ApiConnectionResponse canConnect() {
        try {
            final HttpResponse<String> response = performRequest(checkUrl);
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(response.body());

            if (jsonObject.containsKey("error")) {
                final JSONObject errorObject = (JSONObject) jsonObject.get("error");

                if (errorObject.containsKey("code")) {
                    final int errorCode = Integer.parseInt(errorObject.get("code").toString());

                    if (errorCode == 101)
                        return ApiConnectionResponse.INVALID_ACCESS;
                    else if (errorCode == 104)
                        //throw new IpStackConnectionError("You have reached your monthly limit of requests to ipstacks. Upgrade to premium or enter a different api key");
                        return ApiConnectionResponse.REACHED_REQUEST_LIMIT;
                }
            }
        } catch (IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }

        return ApiConnectionResponse.SUCCESS;
    }

    public PlayerLocationInformation getPlayerInformation(Player player) {
        if (this.cache.containsKey(player.getUniqueId()))
            return this.cache.get(player.getUniqueId());

        final String ip = player.getAddress().getAddress().getHostAddress();
        final String url = apiUrl.formatted(ip);

        try {
            final HttpResponse<String> response = performRequest(url);
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(response.body());

            final String continentName = (String) jsonObject.get("continent_name");
            final String countryName = (String) jsonObject.get("country_name");
            final PlayerLocationInformation playerLocationInformation = new PlayerLocationInformation(continentName, countryName);

            this.cache.put(player.getUniqueId(), playerLocationInformation);

            return playerLocationInformation;
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
        return "ipstack.com";
    }
}
