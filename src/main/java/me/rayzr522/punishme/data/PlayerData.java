package me.rayzr522.punishme.data;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Rayzr522 on 6/24/17.
 */
public class PlayerData {
    private UUID uuid;
    private Map<String, Integer> punishments;

    public PlayerData(UUID uuid, Map<String, Integer> punishments) {
        this.uuid = uuid;
        this.punishments = punishments;
    }

    public PlayerData(UUID uuid) {
        this(uuid, new HashMap<>());
    }

    public static PlayerData load(UUID uuid, ConfigurationSection section) {
        Map<String, Integer> punishments = section.getKeys(false)
                .stream()
                .filter(section::isInt)
                .collect(Collectors.toMap(key -> key, key -> section.getInt(key)));

        return new PlayerData(uuid, punishments);
    }

    public Map<?, ?> save() {
        return punishments;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Map<String, Integer> getPunishments() {
        return punishments;
    }

    public void setPunishments(Map<String, Integer> punishments) {
        this.punishments = punishments;
    }

    public int getPunishmentCount(String name) {
        return punishments.getOrDefault(name, 0);
    }

    public void incrementPunishment(String type, int amount) {
        punishments.put(type, Math.max(0, punishments.getOrDefault(type, 0) + amount));
    }

    public void incrementPunishment(String type) {
        incrementPunishment(type, 1);
    }

    public void decrementPunishment(String type, int amount) {
        incrementPunishment(type, -amount);
    }

    public void decrementPunishment(String type) {
        decrementPunishment(type, 1);
    }

}
