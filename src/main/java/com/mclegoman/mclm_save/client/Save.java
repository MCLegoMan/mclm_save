/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.april_fools.AprilFools;
import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.gui.InfoScreen;
import com.mclegoman.mclm_save.client.level.SaveMinecraft;
import com.mclegoman.mclm_save.client.level.SaveWorld;
import com.mclegoman.mclm_save.common.data.Data;
import net.minecraft.client.gui.screen.DeathScreen;
import org.lwjgl.input.Keyboard;
import org.quiltmc.loader.api.ModContainer;

import java.util.ArrayList;
import java.util.List;

public class Save {
	public static void onInitialize(ModContainer mod) {
		AprilFools.init();
		List<String> bootMessage = new ArrayList<>();
		if (Data.version.isDeveloperBuild()) bootMessage.add("This is a developer build of Save. Expect Bugs!");
		String useLegacyMergeSort = System.getProperty("java.util.Arrays.useLegacyMergeSort");
		if (useLegacyMergeSort == null || useLegacyMergeSort.equalsIgnoreCase("false")) {
			if (!bootMessage.isEmpty()) bootMessage.add("");
			bootMessage.add("Please enable \"java.util.Arrays.useLegacyMergeSort\" for stability.");
		}
		if (!bootMessage.isEmpty()) ClientData.minecraft.m_6408915(new InfoScreen("Save", bootMessage, InfoScreen.Type.DIRT, true));
		//SaveMinecraft.loadWorld("mclm_save-test_world");
		SaveMinecraft.currentWorld = new SaveWorld.Builder("mclm_save-test_world").build();
	}
	public static void onTick(ModContainer mod) {
		try {
			if (Keyboard.isKeyDown(50)) SaveMinecraft.currentWorld.save();
		} catch (Exception error) {}
//		if (LevelFile.shouldProcess) {
//			LevelFile.shouldProcess = false;
//			LevelFile.dialog.interrupt();
//			LevelFile.dialog = null;
//			LevelFile.processWorld();
//		}
//		if (LevelFile.shouldLoad != null) {
//			Couple loadData = LevelFile.shouldLoad;
//			if ((boolean) loadData.getFirst()) {
//				LevelFile.shouldLoad = new Couple(false, true);
//				LevelFile.loadWorld((boolean)loadData.getSecond());
//			}
//		}
		if (ClientData.minecraft.f_0723335 instanceof DeathScreen) {
			if (ClientData.minecraft.f_6058446.health > 0) {
				ClientData.minecraft.f_6058446.deathTime = 0;
				ClientData.minecraft.m_6408915(null);
			}
		}
	}
}