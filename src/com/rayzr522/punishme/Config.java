
package com.rayzr522.punishme;

import org.bukkit.entity.Player;

public class Config extends Configuration {

	public static String	PERM_NOPREVENT	= "";
	public static String	PERM_PUNISH		= "";
	public static String	PERM_PUNISHME	= "";

	public static int		TIME_FIRST		= 5;
	public static int		TIME_REPEAT		= 5;

	public static String	COMMAND_BASE	= "/mute";
	public static String	COMMAND_ARGS	= "{player} {time}m {reason}";

	public static String getCommand(Player p, int time, String reason) {
		return COMMAND_BASE + COMMAND_ARGS.replace("{player}", p.getName()).replace("{time}", "" + time).replace("{reason}", reason);
	}

}
