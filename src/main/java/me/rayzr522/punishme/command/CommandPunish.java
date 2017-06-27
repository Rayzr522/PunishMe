package me.rayzr522.punishme.command;

import me.rayzr522.punishme.PunishMe;
import me.rayzr522.punishme.config.Msg;
import me.rayzr522.punishme.config.Punishment;
import me.rayzr522.punishme.data.PlayerData;
import me.rayzr522.punishme.utils.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandPunish implements CommandExecutor {
    private static final String PERMISSION = "PunishMe.command.punish";

    private PunishMe plugin;

    public CommandPunish(PunishMe plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            Msg.send(sender, "no-permission", PERMISSION);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            plugin.getPunishmentManager().getPunishments().forEach((name, data) -> {
                Msg.send(sender, "punishment-info",
                        name,
                        data.getPunishCommand().toString(),
                        data.getUnpunishCommand().toString(),
                        data.getInitialPunishTime(),
                        data.getRepeatPunishTime()
                );
            });
            return true;
        }

        if (args.length < 3) {
            Msg.send(sender, "usage.punish");
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

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player.getUniqueId());

        double time = punishment.getInitialPunishTime() + punishment.getRepeatPunishTime() * playerData.getPunishmentCount(punishType);
        String command = punishment.getPunishCommand(player, time, ArrayUtils.concat(Arrays.copyOfRange(args, 2, args.length), " "));

        Bukkit.getServer().dispatchCommand(sender, command);
        playerData.incrementPunishment(punishType);

        Msg.send(sender, "punished", player.getDisplayName(), playerData.getPunishmentCount(punishType));

        return true;
    }
}
