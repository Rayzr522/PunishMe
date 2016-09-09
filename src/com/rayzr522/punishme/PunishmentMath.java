
package com.rayzr522.punishme;

public class PunishmentMath {

	public static int calcMinutes(int punishments) {
		return Config.TIME_FIRST + Config.TIME_REPEAT * punishments;
	}

}
