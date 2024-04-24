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

public class AprilFoolsHelper {
	public static int attack;
	public static int defence;
	public static int speed;
	public static boolean isAprilFools() {
		LocalDate date = LocalDate.now();
		return (date.getMonth() == Month.APRIL && date.getDayOfMonth() == 1) || SaveConfig.instance.forceAprilFools.value();
	}
	public static String getVersionString(String fallback) {
		return isAprilFools() ? "0.Phantazap" : fallback;
	}
}
