package org.leiers.betterplayerlocations.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.leiers.betterplayerlocations.locationManager.LocationManager;
import org.leiers.betterplayerlocations.model.PlayerLocationInformation;

public class PlayerLocationInformationCommand implements CommandExecutor {
    private final LocationManager locationManager;

    public PlayerLocationInformationCommand(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /" + label + " <player>");
            return true;
        }

        Player player;

        if ((player = Bukkit.getPlayer(args[0])) == null) {
            sender.sendMessage("§cThis player is not online!");
            return true;
        }

        final PlayerLocationInformation playerLocationInformation = locationManager.getPlayerInformation(player);
        sender.sendMessage("§e" + player.getName() + " §7is from §b" + playerLocationInformation.getCountry()
                + " §7(§b" + playerLocationInformation.getContinent() + "§7)");

        return true;
    }
}
