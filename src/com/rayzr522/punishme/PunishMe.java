
package com.rayzr522.punishme;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.punishme.cmd.CommandPunishMe;

public class PunishMe extends JavaPlugin implements Listener {

	private Logger			logger;
	private ConfigManager	cm;

	@Override
	public void onEnable() {

		logger = getLogger();

		cm = new ConfigManager(this);
		Configuration.init(this);
		if (!Configuration.loadFromJar("messages.yml", true)) {
			System.err.println("Failed to load config files!");
			System.err.println("Gonna go die in a hole now...");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		load();

		getCommand("punishme").setExecutor(new CommandPunishMe(this));

		logger.info(versionText() + " enabled");

	}

	public void load() {

		Msg.load(cm.getOrCreate("messages.yml"));
		Players.load("players.yml");
		new Config().load("config.yml");

	}

	public void save() {

		cm.saveConfig("players.yml", Players.save());

	}

	@Override
	public void onDisable() {

		save();
		logger.info(versionText() + " disabled");

	}

	public String versionText() {

		return getDescription().getName() + " v" + getDescription().getVersion();

	}

}
