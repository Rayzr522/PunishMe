
package com.rayzr522.punishme;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.punishme.cmd.CommandPunish;
import com.rayzr522.punishme.cmd.CommandPunishMe;

public class PunishMe extends JavaPlugin implements Listener {

	private Logger	logger;
	private Config	config;

	@Override
	public void onEnable() {

		logger = getLogger();

		config = new Config();

		Configuration.init(this);
		if (!Configuration.loadFromJar("messages.yml", true)) {
			System.err.println("Failed to load config files!");
			System.err.println("Gonna go die in a hole now...");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		load();

		getCommand("punish").setExecutor(new CommandPunish(this));
		getCommand("punishme").setExecutor(new CommandPunishMe(this));

		getServer().getPluginManager().registerEvents(this, this);

		logger.info(versionText() + " enabled");

	}

	public void load() {

		Msg.load(Configuration.getConfig("messages.yml"));
		Players.load("players.yml");
		config.load("config.yml");

	}

	public void save() {

		Configuration.saveConfig(Players.save(), "players.yml");

	}

	public Config config() {
		return config;
	}

	@Override
	public void onDisable() {

		save();
		logger.info(versionText() + " disabled");

	}

	public String versionText() {

		return getDescription().getName() + " v" + getDescription().getVersion();

	}

	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {

		if (e.getPlayer().hasPermission(config.PERM_NOPREVENT)) { return; }

		String msg = e.getMessage();
		String cmd = "/" + config.COMMAND_BASE;
		if (msg.equalsIgnoreCase(cmd) || (msg.indexOf(" ") != -1 && msg.substring(0, msg.indexOf(" ")).equalsIgnoreCase(cmd))) {
			Msg.send(e.getPlayer(), "no-permission");
			e.setCancelled(true);
		}

	}

}
