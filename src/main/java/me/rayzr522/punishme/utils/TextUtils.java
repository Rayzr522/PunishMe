package me.rayzr522.punishme.utils;

import org.bukkit.ChatColor;

import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtils {
    public static final Pattern UNSAFE_CHARS = Pattern.compile("[^a-z0-9]");

    /**
     * Converts ampersand (&) color codes to use {@link ChatColor#COLOR_CHAR} via {@link ChatColor#translateAlternateColorCodes(char, String)}.
     *
     * @param text The text to colorize.
     * @return The colorized text.
     */
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Opposite of {@link #colorize(String)}, replaces {@link ChatColor#COLOR_CHAR} color codes with ampersand (&) color codes.
     *
     * @param text The text to uncolorize.
     * @return The uncolorized text.
     */
    public static String uncolorize(String text) {
        return text.replace(ChatColor.COLOR_CHAR, '&');
    }

    /**
     * Removes all {@link ChatColor#COLOR_CHAR} color codes from a String.
     *
     * @param text The text to strip the color from.
     * @return The stripped text.
     */
    public static String stripColor(String text) {
        return ChatColor.stripColor(text);
    }

    /**
     * Converts a piece of text into a common enum format, replacing spaces with underscores and converting to uppercase.
     *
     * @param text The text to format.
     * @return The formatted text.
     */
    public static String enumFormat(String text) {
        return text.trim().toUpperCase().replace(" ", "_");
    }

    // Yes, this is from Essentials. Stop judging me.

    /**
     * Ensures a String is safe to use as a key in a config.
     *
     * @param text The text to format.
     * @return The formatted and safe text.
     */
    public static String safeString(String text) {
        return UNSAFE_CHARS.matcher(text.toLowerCase(Locale.ENGLISH)).replaceAll("_");
    }
}
