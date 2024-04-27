/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.level.LevelFile;
import org.quiltmc.loader.api.ModContainer;

public class Save {
	public static void onInitialize(ModContainer mod) {
	}
	public static void onTick(ModContainer mod) {
		if (LevelFile.shouldProcess) {
			LevelFile.shouldProcess = false;
			LevelFile.processWorld();
		}
		if (LevelFile.shouldLoad) {
			LevelFile.shouldLoad = false;
			LevelFile.loadWorld();
		}
	}
}