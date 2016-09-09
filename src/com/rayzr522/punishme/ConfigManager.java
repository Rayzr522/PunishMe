
package com.rayzr522.punishme;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private PunishMe plugin;

	public ConfigManager(PunishMe plugin) {

		this.plugin = plugin;

		if (!configYmlExists()) {

			plugin.saveResource("config.yml", true);

		}

	}

	public boolean configYmlExists() {

		return getFile("config.yml").exists();

	}

	public YamlConfiguration getConfig(String path) {

		return YamlConfiguration.loadConfiguration(getFile(path));

	}

	public YamlConfiguration getOrCreate(String path) {

		File file = getFile(path);

		if (!file.exists()) {
			if (plugin.getResource(path) != null) {
				plugin.saveResource(path, false);
			} else {
				try {
					file.createNewFile();
				} catch (Exception e) {
					System.err.println("Failed to create file at '" + path + "'");
					e.printStackTrace();
				}
			}
		}

		return YamlConfiguration.loadConfiguration(file);

	}

	public File getFile(String path) {

		return new File(plugin.getDataFolder() + File.separator + path);

	}

	public void backupConfig() {

		if (!configYmlExists()) { return; }

		File backupFile = getFile("config-backup.yml");

		if (backupFile.exists()) {
			backupFile.delete();
		}

		File configYml = getFile("config.yml");
		configYml.renameTo(backupFile);

		plugin.saveResource("config.yml", true);

	}

	public void saveConfig(String path, YamlConfiguration config) {

		try {

			File file = getFile(path);

			if (!file.exists()) {

				file.createNewFile();

			}

			config.save(file);

		} catch (Exception e) {

			System.err.println("Failed to save config file at '" + path + "'");
			e.printStackTrace();

		}

	}

}
