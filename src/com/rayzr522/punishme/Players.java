
package com.rayzr522.punishme;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Players {

	public static HashMap<UUID, Integer> punishments = new HashMap<UUID, Integer>();

	public static void load(String path) {

		YamlConfiguration config = Configuration.getConfig(path);

		for (String key : config.getKeys(false)) {

			punishments.put(UUID.fromString(key), config.getInt(key));

		}

	}

	public static int get(Player p) {
		return get(p.getUniqueId());
	}

	public static int get(UUID id) {
		if (!punishments.containsKey(id)) {
			punishments.put(id, 0);
		}
		return punishments.get(id);
	}

	public static int incr(Player p) {

		return incr(p.getUniqueId());

	}

	public static int incr(UUID id) {

		int num = get(id) + 1;
		punishments.put(id, num);
		return num;

	}

	public static YamlConfiguration save() {
		YamlConfiguration config = new YamlConfiguration();

		for (Entry<UUID, Integer> entry : punishments.entrySet()) {
			config.set(entry.getKey().toString(), entry.getValue());
		}

		return config;
	}
}
