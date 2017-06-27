package me.rayzr522.punishme.command;

import me.rayzr522.punishme.PunishMe;
import me.rayzr522.punishme.config.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandPunishMe implements CommandExecutor {
    private static String PERMISSION = "PunishMe.punishme";

    private PunishMe plugin;

    public CommandPunishMe(PunishMe plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            Msg.send(sender, "no-permission", PERMISSION);
            return true;
        }

        if (args.length < 1) {
            Msg.send(sender, "version-info", plugin.getName(), plugin.getDescription().getVersion());
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("reload")) {
            plugin.save();
            plugin.reload();
            Msg.send(sender, "config-reloaded");
        } else if (sub.equals("save")) {
            plugin.save();
            Msg.send(sender, "config-saved");
        } else {
            Msg.send(sender, "usage.punishme");
        }

        return true;
    }

}
