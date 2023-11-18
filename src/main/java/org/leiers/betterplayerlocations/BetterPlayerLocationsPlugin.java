package org.leiers.betterplayerlocations;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.leiers.betterplayerlocations.commands.PlayerLocationInformationCommand;
import org.leiers.betterplayerlocations.exception.IpStackConnectionError;
import org.leiers.leplugin.LePlugin;

public final class BetterPlayerLocationsPlugin extends LePlugin implements Listener {
    private LocationManager locationManager;
    private boolean disable = false;

    @Override
    public void loading() {
        final String apiKey = getConfig().getString("ipstack_key");
        this.locationManager = new LocationManager(apiKey);

        try {
            this.locationManager.canConnect();
            getLogger().info("Successfully connected to ipstack.com");
        } catch (IpStackConnectionError e) {
            getLogger().severe(e.getMessage());
            disable = true;
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
        locationManager.removeFromCache(event.getPlayer());
    }

    @Override
    public long getSpigotResourceId() {
        return 0;
    }

    @Override
    public String getSpigotDownloadLink() {
        return null;
    }
}
