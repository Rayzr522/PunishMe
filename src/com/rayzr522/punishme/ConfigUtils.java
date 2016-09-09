
package com.rayzr522.punishme;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ConfigUtils {

	public static String toString(Vector vector) {

		return vector.getX() + ":" + vector.getY() + ":" + vector.getZ();

	}

	public static String toString(Location loc) {

		return toString(loc.getWorld()) + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getPitch() + ":" + loc.getYaw();

	}

	public static String toString(World world) {

		return world.getUID().toString();

	}

	public static Vector vector(String vec) {

		String[] split = vec.split(":");

		if (split.length != 3) { return null; }

		return new Vector(d(split[0]), d(split[1]), d(split[2]));

	}

	public static Location location(String loc) {

		String[] split = loc.split(":");
		if (split.length != 6) { return null; }

		if (world(split[0]) == null) {
			System.err.println("'" + split[0] + "' is an invalid world UUID!");
			return null;
		}

		return new Location(world(split[0]), d(split[1]), d(split[2]), d(split[3]), f(split[4]), f(split[5]));

	}

	public static World world(String world) {

		return Bukkit.getWorld(UUID.fromString(world));

	}

	public static int i(String text) {
		return Integer.parseInt(text);
	}

	public static double d(String text) {
		return Double.parseDouble(text);
	}

	public static float f(String text) {
		return Float.parseFloat(text);
	}

}
