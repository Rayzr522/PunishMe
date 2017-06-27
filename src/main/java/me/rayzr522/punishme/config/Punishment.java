package me.rayzr522.punishme.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Punishment {
    private Command punishCommand;
    private Command unpunishCommand;

    private double initialPunishTime;
    private double repeatPunishTime;

    private Punishment(String punishCommand, String unpunishCommand, double initialPunishTime, double repeatPunishTime) {
        this.punishCommand = new Command(punishCommand);
        this.unpunishCommand = new Command(unpunishCommand);
        this.initialPunishTime = initialPunishTime;
        this.repeatPunishTime = repeatPunishTime;
    }

    /**
     * @param config The {@link org.bukkit.configuration.Configuration} to load this {@link Punishment} from.
     * @return The loaded {@link Punishment}.
     * @throws ConfigurationException if any part of the config is malformed.
     */
    public static Punishment load(ConfigurationSection config) throws ConfigurationException {
        // Check commands
        if (!config.isString("command.punish")) {
            throw new ConfigurationException("command.punish must be a string");
        }

        if (!config.isString("command.unpunish")) {
            throw new ConfigurationException("command.unpunish must be a string");
        }

        // Check times
        if (!config.isDouble("time.first") && !config.isInt("time.first")) {
            throw new ConfigurationException("time.first must be a decimal number or integer");
        }

        if (!config.isDouble("time.repeat") && !config.isInt("time.repeat")) {
            throw new ConfigurationException("time.repeat must be a decimal number or integer");
        }

        return new Punishment(
                // Command to execute when running /punish
                config.getString("command.punish"),
                // Command to execute when running /unpunish
                config.getString("command.unpunish"),
                // How long the punishment duration should be the first time
                config.getDouble("time.first"),
                // How much to increase the punishment duration for each repeated offense
                config.getDouble("time.repeat")
        );
    }

    /**
     * @param player The player to punish.
     * @param time   The duration to punish them for.
     * @param reason The reason for punishment.
     * @return The (formatted) command to execute when running <code>/punish</code>.
     */
    public String getPunishCommand(Player player, double time, String reason) {
        return punishCommand.toString()
                .replace("{player}", player.getName())
                .replace("{time}", String.valueOf(time))
                .replace("{reason}", reason);
    }

    public Command getPunishCommand() {
        return punishCommand;
    }

    /**
     * @param player The player to pardon.
     * @return The (formatted) command to execute when running <code>/unpunish</code>.
     */
    public String getUnpunishCommand(Player player) {
        return unpunishCommand.toString()
                .replace("{player}", player.getName());
    }

    public Command getUnpunishCommand() {
        return unpunishCommand;
    }

    /**
     * @return How long the punishment duration should be the first time.
     */
    public double getInitialPunishTime() {
        return initialPunishTime;
    }

    /**
     * @return How much to increase the punishment duration for each repeated offense.
     */
    public double getRepeatPunishTime() {
        return repeatPunishTime;
    }

    /**
     * Represents a command that can be executed by a {@link org.bukkit.entity.Player} or the {@link org.bukkit.command.ConsoleCommandSender console}.
     */
    public static class Command {
        private String base;
        private String parameters;

        /**
         * Creates a new {@link Command} from a complete command String, deconstructing it into a base and parameters.
         *
         * @param command The command String.
         */
        public Command(String command) {
            Objects.requireNonNull(command, "command cannot be null!");

            base = command.substring(0, command.indexOf(' '));
            parameters = command.substring(command.indexOf(' ') + 1);
        }

        /**
         * @return The base command name.
         */
        public String getBase() {
            return base;
        }

        /**
         * @return The parameters to the command.
         */
        public String getParameters() {
            return parameters;
        }

        /**
         * @return The base, a space, and then the parameters.
         * @see #getBase()
         * @see #getParameters()
         */
        @Override
        public String toString() {
            return String.format("%s %s", base, parameters);
        }
    }
}
