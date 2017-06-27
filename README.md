# PunishMe
Just a quick note, this is another request by *xOrlxndo*. I hope you enjoy!

**PunishMe** is a Bukkit plugin which allows custom incremental punishments for players. By default this is set up to work with [MuteManager](http://dev.bukkit.org/bukkit-plugins/mutemanager/) by *cnaude*.

## Commands

### `/punish list`
**Permission:** `PunishMe.command.punish`  
**Description:** Lists all available punishments

### `/punish <punishment> <player> <reason>`
**Permissions:** `PunishMe.command.punish`, `PunishMe.punishment.<punishment>`  
**Description:** Punishes a player with the given punishment type for the given reason.

### `/unpunish <punishment> <player>`
**Permissions:** `PunishMe.command.unpunish`, `PunishMe.punishment.<punishment>`  
**Description:** Unpunishs a player for the given punishment type.

### `/punishme`
**Permission:** `PunishMe.command.punishme`  
**Description:** Shows version information.

### `/punishme save`
**Permission:** `PunishMe.command.punishme`  
**Description:** Saves all player data.

### `/punishme reload`
**Permission:** `PunishMe.command.punishme`  
**Description:** Saves all player data and then reloads all config files.