
package com.rayzr522.punishme;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Msg {

    // Change this if you want it to list the messages when they load
    private static final boolean           DEBUG    = false;
    private static HashMap<String, String> messages = new HashMap<String, String>();

    public static void load(YamlConfiguration config) {

        messages.clear();

        if (DEBUG) {
            System.out.println("Loading messages:");
        }

        for (String key : config.getKeys(true)) {

            if (config.get(key) instanceof String) {
                messages.put(key, config.getString(key));
            }

        }

        Pattern regex = Pattern.compile("\\[\\[[a-zA-Z-_.]{1,}\\]\\]");

        for (Entry<String, String> entry : messages.entrySet()) {

            String msg = entry.getValue();
            Matcher matcher = regex.matcher(msg);

            while (matcher.find()) {

                String inputKey = matcher.group();
                inputKey = inputKey.substring(2, inputKey.length() - 2);

                if (messages.containsKey(inputKey)) {

                    msg = msg.replaceFirst(regex.pattern(), messages.get(inputKey));

                }

            }

            if (DEBUG) {
                System.out.println(entry.getKey() + " - " + msg);
            }
            messages.put(entry.getKey(), msg);

        }

    }

    public static void send(Player p, String key, String... strings) {

        String msg = get(key);

        for (int i = 0; i < strings.length; i++) {
            msg = msg.replace("{" + i + "}", strings[i]);
        }

        p.sendMessage(msg);

    }

    public static void send(CommandSender sender, String key, String... strings) {

        String msg = get(key);

        for (int i = 0; i < strings.length; i++) {
            msg = msg.replace("{" + i + "}", strings[i]);
        }

        sender.sendMessage(msg);

    }

    public static String get(String key) {

        return messages.containsKey(key) ? TextUtils.colorize(messages.get(key)) : key;

    }

}
