
package com.rayzr522.punishme.cmd;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.punishme.Config;
import com.rayzr522.punishme.Msg;
import com.rayzr522.punishme.Players;
import com.rayzr522.punishme.PunishMe;

public class CommandUnPunish implements CommandExecutor {

	private PunishMe plugin;

	public CommandUnPunish(PunishMe plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Config config = plugin.config();

		if (!sender.hasPermission(config.PERM_PUNISH)) {

			Msg.send(sender, "no-permission");
			return true;

		}

		if (args.length < 1) {

			Msg.send(sender, "usage.unpunish");
			return true;

		}

		List<Player> matches = Bukkit.matchPlayer(args[0]);
		if (matches.size() < 1) {
			Msg.send(sender, "no-player", args[0]);
			return true;
		} else if (matches.size() > 1) {
			Msg.send(sender, "multiple-players", args[0]);
			return true;
		}

		Player p = matches.get(0);
		Bukkit.getServer().dispatchCommand(sender, config.getUnpunishCommand(p));

		Players.decr(p);
		Msg.send(sender, "unpunished", p.getDisplayName(), "" + Players.get(p));

		return true;

	}

}
