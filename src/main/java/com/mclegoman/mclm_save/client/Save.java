/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.april_fools.AprilFools;
import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.util.Couple;
import net.minecraft.client.gui.screen.DeathScreen;
import org.quiltmc.loader.api.ModContainer;

public class Save {
	public static void onInitialize(ModContainer mod) {
		AprilFools.init();
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
	}
}