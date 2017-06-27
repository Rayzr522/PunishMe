package me.rayzr522.punishme.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Locale;

public class Configuration {
    public static JavaPlugin plugin;
    public static File dataFolder;

    public static void init(JavaPlugin plugin) {
        Configuration.plugin = plugin;
        dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public static File getFile(String path) {
        return new File(dataFolder.getPath(), path.replace('/', File.separatorChar));
    }

    public static YamlConfiguration getConfig(String path) {
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    public static boolean loadFromJar(String path, boolean update) {
        if (plugin.getResource(path) == null) {
            return false;
        }

        if (update && getFile(path).exists()) {
            File file = getFile(path);
            try {
                Files.move(file.toPath(), getFile("_updating_" + path).toPath());
            } catch (IOException e) {
                System.err.println("Failed to update config file '" + path + "'");
                e.printStackTrace();
                return false;
            }

            plugin.saveResource(path, false);
            YamlConfiguration config1 = getConfig(path);
            YamlConfiguration config2 = getConfig("_updating_" + path);

            for (String key : config1.getKeys(true)) {
                if (!config2.contains(key)) {
                    config2.set(key, config1.get(key));
                }
            }

            try {
                Files.delete(getFile(path).toPath());
                Files.delete(getFile("_updating_" + path).toPath());
            } catch (IOException e) {
                System.err.println("Failed to update config file '" + path + "'");
                e.printStackTrace();
            }

            saveConfig(config2, path);

        } else {
            plugin.saveResource(path, false);
        }

        return true;

    }

    public static boolean loadFromJar(String path) {
        return loadFromJar(path, false);
    }

    public static boolean saveConfig(YamlConfiguration config, String path) {
        try {
            config.save(getFile(path));
            return true;
        } catch (Exception e) {
            System.err.println("Failed to save config file '" + path + "':");
            e.printStackTrace();
            return false;
        }
    }

    public void load(String path) {
        YamlConfiguration config = getConfig(path);
        if (config.getKeys(true).size() < 1) {
            save(path);
            return;
        }

        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            Object val = config.get(path(field));
            if (val == null) {
                continue;
            }
            try {
                set(field, this, val);
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading '" + path + "': Invalid type '" + field.getType().getCanonicalName() + "' for field '" + field.getName() + "'");
            } catch (IllegalAccessException | NullPointerException e) {

            }
        }
    }

    public void save(String path) {
        YamlConfiguration config = getConfig(path);

        for (Field field : getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }

            try {
                config.set(path(field), field.get(this));
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading '" + path + "': Invalid type '" + field.getType().getCanonicalName() + "' for field '" + field.getName() + "'");
            } catch (IllegalAccessException e) {

            }
        }

        saveConfig(config, path);
    }

    private void set(Field field, Object inst, Object val) throws IllegalArgumentException, IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(inst, val);
        field.setAccessible(accessible);
    }

    private String path(Field field) {
        return field.getName().replace("_", ".").toLowerCase(Locale.getDefault());
    }

}
