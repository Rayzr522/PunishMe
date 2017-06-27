package me.rayzr522.punishme.command;

import me.rayzr522.punishme.PunishMe;
import me.rayzr522.punishme.config.Msg;
import me.rayzr522.punishme.config.Punishment;
import me.rayzr522.punishme.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnPunish implements CommandExecutor {
    private static final String PERMISSION = "PunishMe.command.unpunish";

    private PunishMe plugin;

    public CommandUnPunish(PunishMe plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            Msg.send(sender, "no-permission", PERMISSION);
            return true;
        }

        if (args.length < 2) {
            Msg.send(sender, "usage.unpunish");
            return true;
        }

        String punishType = args[0];
        Punishment punishment = plugin.getPunishmentManager().getPunishment(punishType);

        if (punishment == null) {
            Msg.send(sender, "invalid-punishment", punishType);
            return true;
        }

        String punishPerm = "PunishMe.punishment." + punishType;
        if (!sender.hasPermission(punishPerm)) {
            Msg.send(sender, "no-permission", punishPerm);
            return true;
        }

        String playerName = args[1];
        @SuppressWarnings("deprecation")
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            Msg.send(sender, "no-player", playerName);
            return true;
        }

        Bukkit.getServer().dispatchCommand(sender, punishment.getUnpunishCommand(player));

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());
        playerData.decrementPunishment(punishType);

        Msg.send(sender, "unpunished", player.getDisplayName(), String.valueOf(playerData.getPunishmentCount(punishType)));

        return true;
    }

}
