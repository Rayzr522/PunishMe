
package com.rayzr522.punishme.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.rayzr522.punishme.Config;
import com.rayzr522.punishme.Msg;
import com.rayzr522.punishme.PunishMe;

public class CommandPunishMe implements CommandExecutor {

	private PunishMe plugin;

	public CommandPunishMe(PunishMe plugin) {

		this.plugin = plugin;

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Config config = plugin.config();

		if (!sender.hasPermission(config.PERM_PUNISHME)) {

			Msg.send(sender, "no-permission");
			return true;

		}

		if (args.length < 1) {

			PluginDescriptionFile pdf = plugin.getDescription();
			Msg.send(sender, "version-info", pdf.getName(), pdf.getVersion());
			return true;

		}

		String cmd = args[0].toLowerCase();

		if (cmd.equals("reload")) {

			plugin.reloadConfig();
			plugin.load();
			Msg.send(sender, "config-reloaded");

		} else {

			Msg.send(sender, "usage.punishme");

		}

		return true;

	}

}
