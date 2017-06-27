package me.rayzr522.punishme.config;

import me.rayzr522.punishme.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Msg {
    private static final Pattern KEY_PATTERN = Pattern.compile("\\[\\[[a-zA-Z_.-]+]]");

    private static HashMap<String, String> messages = new HashMap<String, String>();

    public static void load(YamlConfiguration config) {
        messages.clear();

        for (String key : config.getKeys(true)) {
            if (config.isList(key)) {
                messages.put(key, config.getList(key).stream().map(Objects::toString).collect(Collectors.joining("\n")));
            } else {
                messages.put(key, config.get(key).toString());
            }
        }

        for (Entry<String, String> entry : messages.entrySet()) {

            String msg = entry.getValue();
            Matcher matcher = KEY_PATTERN.matcher(msg);

            while (matcher.find()) {
                String inputKey = matcher.group();
                inputKey = inputKey.substring(2, inputKey.length() - 2);

                if (messages.containsKey(inputKey)) {
                    msg = msg.replaceFirst(KEY_PATTERN.pattern(), messages.get(inputKey));
                }
            }

            messages.put(entry.getKey(), msg);
        }
    }

    public static void send(CommandSender sender, String key, Object... objects) {
        String message = get(key);

        for (int i = 0; i < objects.length; i++) {
            // Use Objects.toString to handle null values
            message = message.replace("{" + i + "}", Objects.toString(objects[i]));
        }

        sender.sendMessage(message);
    }

    public static String get(String key) {
        return messages.containsKey(key) ? TextUtils.colorize(messages.get(key)) : key;
    }

}
