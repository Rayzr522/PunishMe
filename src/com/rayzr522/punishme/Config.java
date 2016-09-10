
package com.rayzr522.punishme;

import org.bukkit.entity.Player;

public class Config extends Configuration {

	public String	PERM_NOPREVENT	= "PunishMe.noprevent";
	public String	PERM_PUNISH		= "PunishMe.punish";
	public String	PERM_PUNISHME	= "PunishMe.punishme";

	public int		TIME_FIRST		= 5;
	public int		TIME_REPEAT		= 5;

	public String	COMMAND_BASE	= "mute";
	public String	COMMAND_ARGS	= "{player} {time}m {reason}";

	public String getCommand(Player p, int time, String reason) {
		return COMMAND_BASE + " " + COMMAND_ARGS.replace("{player}", p.getName()).replace("{time}", "" + time).replace("{reason}", reason);
	}

}
