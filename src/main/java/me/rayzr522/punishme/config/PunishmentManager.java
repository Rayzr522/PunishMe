package me.rayzr522.punishme.config;

import me.rayzr522.punishme.PunishMe;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rayzr522 on 6/24/17.
 */
public class PunishmentManager {
    private Map<String, Punishment> punishments = new HashMap<>();
    private PunishMe plugin;

    public PunishmentManager(PunishMe plugin) {
        this.plugin = plugin;
    }

    /**
     * @param name The name of the punishment.
     * @return The {@link Punishment} configuration with that name, or <code>null</code> if none was found.
     */
    public Punishment getPunishment(String name) {
        return punishments.get(name);
    }

    /**
     * Loads all {@link Punishment}s from a config.
     *
     * @param config The config to load punishments from.
     */
    public void load(ConfigurationSection config) {
        punishments.clear();

        for (String key : config.getKeys(false)) {
            try {
                punishments.put(key, Punishment.load(config.getConfigurationSection(key)));
            } catch (ConfigurationException e) {
                plugin.getLogger().severe(String.format("Failed to load config: %s", e.getMessage()));
            }
        }
    }

    public Map<String, Punishment> getPunishments() {
        return punishments;
    }
}
