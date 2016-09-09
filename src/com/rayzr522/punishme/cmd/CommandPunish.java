
package com.rayzr522.punishme.cmd;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.punishme.ArrayUtils;
import com.rayzr522.punishme.Config;
import com.rayzr522.punishme.Msg;
import com.rayzr522.punishme.Players;
import com.rayzr522.punishme.PunishmentMath;

public class CommandPunish implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission(Config.PERM_PUNISH)) {

			Msg.send(sender, "no-permission");
			return true;

		}

		if (args.length < 2) {

			Msg.send(sender, "usage.punish");
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

		int time = PunishmentMath.calcMinutes(Players.get(p));
		String punishment = Config.getCommand(p, time, ArrayUtils.concat(Arrays.copyOfRange(args, 2, args.length), " "));

		Bukkit.getServer().dispatchCommand(sender, punishment);

		Players.incr(p);
		Msg.send(sender, "punished", p.getDisplayName(), "" + Players.get(p));

		return true;

	}

}
