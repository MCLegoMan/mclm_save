/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.common.util.Couple;
import org.quiltmc.loader.api.ModContainer;

public class Save {
	public static void onInitialize(ModContainer mod) {
	}
	public static void onTick(ModContainer mod) {
		if (LevelFile.shouldProcess) {
			LevelFile.shouldProcess = false;
			LevelFile.dialog.interrupt();
			LevelFile.dialog = null;
			LevelFile.processWorld();
		}
		if (LevelFile.shouldLoad != null) {
			Couple loadData = LevelFile.shouldLoad;
			if ((boolean) loadData.getFirst()) {
				LevelFile.shouldLoad = new Couple(false, true);
				LevelFile.loadWorld((boolean)loadData.getSecond());
			}
		}
//		if (ClientData.minecraft.f_0723335 instanceof DeathScreen) {
//			if (ClientData.minecraft.f_6058446.health > 0) {
//				ClientData.minecraft.f_6058446.deathTime = 0;
//				ClientData.minecraft.m_6408915(null);
//			}
//		}
	}
}