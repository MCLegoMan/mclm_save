/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.april_fools;

import com.mclegoman.mclm_save.config.SaveConfig;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class AprilFools {
	public static List<String> playerNames = new ArrayList<>();
	public static int nameChance;
	public static int name;
	public static int attack;
	public static int defence;
	public static int speed;
	public static void init() {
		playerNames.add("Phantazap");
		playerNames.add("MCLegoMan");
	}
	public static String getPlayerName() {
		if (nameChance == 1200) return "Herobrine";
		else return playerNames.get(name);
	}
	public static boolean isAprilFools() {
		LocalDate date = LocalDate.now();
		return (date.getMonth() == Month.APRIL && date.getDayOfMonth() == 1) || SaveConfig.instance.forceAprilFools.value();
	}
	public static String getVersionString(String fallback) {
		return isAprilFools() ? "Terraria 3" : fallback;
	}
}
