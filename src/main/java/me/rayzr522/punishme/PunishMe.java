package me.rayzr522.punishme;

import me.rayzr522.punishme.command.CommandPunish;
import me.rayzr522.punishme.command.CommandPunishMe;
import me.rayzr522.punishme.command.CommandUnPunish;
import me.rayzr522.punishme.config.Msg;
import me.rayzr522.punishme.config.PunishmentManager;
import me.rayzr522.punishme.data.PlayerManager;
import me.rayzr522.punishme.event.CommandListener;
import me.rayzr522.punishme.utils.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PunishMe extends JavaPlugin implements Listener {
    private PunishmentManager punishmentManager;
    private PlayerManager playerManager;

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Initialize utilities
        Configuration.init(this);

        // Initialize managers
        punishmentManager = new PunishmentManager(this);
        playerManager = new PlayerManager(this);

        // Load Configs
        reload();

        // Command handlers
        getCommand("punish").setExecutor(new CommandPunish(this));
        getCommand("unpunish").setExecutor(new CommandUnPunish(this));
        getCommand("punishme").setExecutor(new CommandPunishMe(this));

        // Event handlers
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        // Save the punishment data every 5 minutes (5m * 60s * 20t = 6000 ticks)
        new BukkitRunnable() {
            public void run() {
                save();
            }
        }.runTaskTimer(this, 0, 6000);
    }

    @Override
    public void onDisable() {
        save();
    }

    /**
     * (Re)loads all data/config files from the disk.
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        if (!Configuration.getFile("messages.yml").exists()) {
            saveResource("messages.yml", false);
        }

        Msg.load(Configuration.getConfig("messages.yml"));

        punishmentManager.load(getConfig().getConfigurationSection("punishments"));
        playerManager.load(Configuration.getConfig("players.yml"));
    }

    /**
     * Save all data that must persist to the disk.
     */
    public void save() {
        Configuration.saveConfig(playerManager.save(), "players.yml");
    }

    /**
     * @return The {@link PunishmentManager} for this plugin.
     */
    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    /**
     * @return The {@link PlayerManager} instance.
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public String versionText() {
        return getDescription().getName() + " v" + getDescription().getVersion();
    }
}
