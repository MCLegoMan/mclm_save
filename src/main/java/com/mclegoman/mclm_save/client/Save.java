/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.client.util.Debug;
import org.quiltmc.loader.api.ModContainer;

public class Save {
	public static void onInitialize(ModContainer mod) {
	}
	public static void onTick(ModContainer mod) {
		if (LevelFile.shouldLoad) {
			LevelFile.shouldLoad = false;
			LevelFile.loadWorld();
		}
		Debug.currentScreen = ClientData.minecraft.f_0723335;
		Debug.setDebug();
	}
}