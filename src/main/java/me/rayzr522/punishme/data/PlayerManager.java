package me.rayzr522.punishme.data;

import me.rayzr522.punishme.PunishMe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerManager {
    private Map<UUID, PlayerData> playerData = new HashMap<>();
    private PunishMe plugin;

    public PlayerManager(PunishMe plugin) {
        this.plugin = plugin;
    }

    /**
     * @param uuid The {@link UUID} of the {@link org.bukkit.entity.Player} to get the {@link PlayerData} for.
     * @return The (non-null) {@link PlayerData}.
     */
    public PlayerData getPlayerData(UUID uuid) {
        PlayerData data = playerData.get(uuid);

        if (data == null) {
            data = new PlayerData(uuid);
            playerData.put(uuid, data);
        }

        return data;
    }

    /**
     * Loads all {@link PlayerData} from a config.
     *
     * @param config The {@link ConfigurationSection} to load from.
     */
    public void load(ConfigurationSection config) {
        playerData.clear();

        for (String key : config.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                playerData.put(uuid, PlayerData.load(uuid, config.getConfigurationSection(key)));
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to reload PlayerData for key '" + key + "'", e);
            }
        }
    }

    /**
     * Saves all {@link PlayerData} to a config.
     *
     * @return A {@link YamlConfiguration} with all data saved.
     */
    public YamlConfiguration save() {
        YamlConfiguration config = new YamlConfiguration();

        playerData.forEach((id, data) -> {
            config.createSection(id.toString(), data.save());
        });

        return config;
    }
}
