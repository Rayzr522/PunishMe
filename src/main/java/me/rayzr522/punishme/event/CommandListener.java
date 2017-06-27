package me.rayzr522.punishme.event;

import me.rayzr522.punishme.PunishMe;
import me.rayzr522.punishme.config.Msg;
import me.rayzr522.punishme.config.Punishment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Map;

/**
 * Created by Rayzr522 on 6/24/17.
 */
public class CommandListener implements Listener {
    private static final String PREVENT_PUNISH = "PunishMe.prevent.%s.punish";
    private static final String PREVENT_UNPUNISH = "PunishMe.prevent.%s.unpunish";

    private PunishMe plugin;

    public CommandListener(PunishMe plugin) {
        this.plugin = plugin;
    }

    /**
     * Called by the EventHandler when a command is executed. This is to prevent
     * the punishment command from being used normally.
     *
     * @param e The {@link PlayerCommandPreprocessEvent}.
     */
    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        for (Map.Entry<String, Punishment> entry : plugin.getPunishmentManager().getPunishments().entrySet()) {
            String name = entry.getKey();
            Punishment punishment = entry.getValue();

            // Check to make sure they aren't using commands they aren't supposed to
            if (match(punishment.getPunishCommand().getBase(), e.getMessage())) {
                if (e.getPlayer().hasPermission(String.format(PREVENT_PUNISH, name))) {
                    Msg.send(e.getPlayer(), "please-use.punish");
                    e.setCancelled(true);
                    return;
                }
            } else if (match(punishment.getUnpunishCommand().getBase(), e.getMessage())) {
                if (e.getPlayer().hasPermission(String.format(PREVENT_UNPUNISH, name))) {
                    Msg.send(e.getPlayer(), "please-use.unpunish");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    private boolean match(String command, String match) {
        command = command.startsWith("/") ? command : "/" + command;
        if (match.equalsIgnoreCase(command) || (match.indexOf(" ") != -1 && match.substring(0, match.indexOf(" ")).equalsIgnoreCase(command))) {
            return true;
        }
        return false;
    }
}
