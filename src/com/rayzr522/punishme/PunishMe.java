
package com.rayzr522.punishme;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.rayzr522.punishme.cmd.CommandPunish;
import com.rayzr522.punishme.cmd.CommandPunishMe;
import com.rayzr522.punishme.cmd.CommandUnPunish;

public class PunishMe extends JavaPlugin implements Listener {

	// The logger (used for logging info to the console)
	// You can also use System.out.println like usual, and it'll print to the
	// server's console
	private Logger	logger;
	// The config file instance
	private Config	config;

	/**
	 * Called when the plugin is enabled.
	 */
	@Override
	public void onEnable() {

		// Get the logger
		logger = getLogger();

		// Initialize the Configuration class with this plugin's data path
		Configuration.init(this);
		// Attempt to load "messages.yml" from the JAR file if it doesn't exist
		if (!Configuration.loadFromJar("messages.yml")) {
			System.err.println("Failed to load config files!");
			System.err.println("Gonna go die in a hole now...");
			// We have failed
			Bukkit.getPluginManager().disablePlugin(this);
		}

		// Initialize the config instance
		config = new Config();

		load();

		// Register command handlers
		getCommand("punish").setExecutor(new CommandPunish(this));
		getCommand("unpunish").setExecutor(new CommandUnPunish(this));
		getCommand("punishme").setExecutor(new CommandPunishMe(this));

		// Register this as an event handler
		getServer().getPluginManager().registerEvents(this, this);

		// Save the punishment data every 5 minutes (5m * 60s * 20t = 6000
		// ticks)
		new BukkitRunnable() {

			public void run() {
				save();
			}

		}.runTaskTimer(this, 0, 6000);

		// Success!
		logger.info(versionText() + " enabled");

	}

	/**
	 * Load all the config files from disk. This does not perform
	 * plugin.reloadConfig().
	 */
	public void load() {

		Msg.load(Configuration.getConfig("messages.yml"));
		Players.load("players.yml");
		config.load("config.yml");

	}

	/**
	 * Save all data that must persist to the disk.
	 */
	public void save() {

		Configuration.saveConfig(Players.save(), "players.yml");

	}

	/**
	 * Get the config instance. Use this instead of getConfig().
	 * 
	 * @return The config instance for this plugin
	 */
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

	/**
	 * Called by the EventHandler when a command is executed. This is to prevent
	 * the punishment command from being used normally.
	 * 
	 * @param e
	 *            the event
	 */
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {

		// Check to make sure they aren't using commands they aren't supposed to
		if (match(config.COMMAND_PUNISH_BASE, e.getMessage())) {
			if (e.getPlayer().hasPermission(config.PERM_PREVENT_PUNISH)) {
				Msg.send(e.getPlayer(), "please-use.punish");
				e.setCancelled(true);
			}
		} else if (match(config.COMMAND_UNPUNISH_BASE, e.getMessage())) {
			if (e.getPlayer().hasPermission(config.PERM_PREVENT_UNPUNISH)) {
				Msg.send(e.getPlayer(), "please-use.unpunish");
				e.setCancelled(true);
			}
		}
	}

	private boolean match(String cmd, String msg) {
		msg = msg.startsWith("/") ? msg : "/" + msg;
		if (msg.equalsIgnoreCase(cmd) || (msg.indexOf(" ") != -1 && msg.substring(0, msg.indexOf(" ")).equalsIgnoreCase(cmd))) { return true; }
		return false;
	}

}
