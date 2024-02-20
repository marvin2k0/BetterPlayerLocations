package org.leiers.betterplayerlocations;

import com.simpleminecode.api.plugin.SimplePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.leiers.betterplayerlocations.commands.PlayerLocationInformationCommand;
import org.leiers.betterplayerlocations.locationManager.IpStackLocationManager;
import org.leiers.betterplayerlocations.locationManager.LocationManager;

public final class BetterPlayerLocationsPlugin extends SimplePlugin implements Listener {
    private LocationManager locationManager;
    private boolean disable = false;

    @Override
    public void loading() {
        final String apiKey = getConfig().getString("ipstack_key");
        this.locationManager = new IpStackLocationManager(apiKey);

        switch (this.locationManager.canConnect()) {
            case SUCCESS -> getLogger().info("Successfully connected to " + locationManager.getWebsite());
            case INVALID_ACCESS -> getLogger().severe("The api key specified in config.yml was not valid");
            // TODO: other cases
        }
    }

    @Override
    public void starting() {
        if (disable) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("location").setExecutor(new PlayerLocationInformationCommand(locationManager));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        locationManager.getCache().remove(event.getPlayer().getUniqueId());
    }
}
