/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.gui.InfoScreen;
import com.mclegoman.mclm_save.client.gui.OpenLoadScreen;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class LevelFile {
	public static File file;
	public static boolean isLoad;
	public static boolean shouldLoad;
	public static void load(boolean isLoad) {
		try {
			OpenLoadScreen screen = new OpenLoadScreen(isLoad);
			screen.start();
		} finally {
			ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Please Wait", InfoScreen.Type.DIRT, false));
		}
	}
	public static void loadWorld() {
		try {
			if (file != null) processWorld(file);
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, error.getLocalizedMessage());
			// We use InfoScreen instead of FatalErrorScreen so the player can use ESC to go back to the game.
			ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
		}
	}
	public static void processWorld(File file) throws IOException {
		ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Processing File", InfoScreen.Type.DIRT, false));
		if (file != null) {
			if (isLoad) {
				try {
					File worldFile = getWorldFile(file);
					if (worldFile != null) {
						if (file.isFile() && file.exists()) {
							FileInputStream file1 = new FileInputStream(worldFile);
							Data.version.sendToLog(Helper.LogType.INFO, "Loading World...");
							net.minecraft.world.World world = (new World()).load(file1);
							file1.close();
							Accessors.MinecraftClient.loadWorld(world);
						}
						ClientData.minecraft.m_6408915(null);
					} else {
						Data.version.sendToLog(Helper.LogType.INFO, "Failed to load world!");
						ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", "World does not exist.", InfoScreen.Type.ERROR, true));
					}
				} catch (Exception error) {
					Data.version.sendToLog(Helper.LogType.INFO, "Failed to load world!");
					ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
				}
			} else {
				try {
					if (!file.getPath().toLowerCase().endsWith(".mclevel")) file = new File(file.getPath() + ".mclevel");
					Data.version.sendToLog(Helper.LogType.INFO, "Saving World...");
					(new World()).save(ClientData.minecraft.f_5854988, file);
					ClientData.minecraft.m_6408915(null);
				} catch (Exception error) {
					Data.version.sendToLog(Helper.LogType.INFO, "Failed to save world!");
					ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to save world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
				}
			}
		}
	}
	public static File getWorldFile(File file) {
		Data.version.sendToLog(Helper.LogType.INFO, "Getting World...");
		String filePath = file.getPath().toLowerCase();
		if (filePath.endsWith(".mclevel")) {
			return file;
		}
		else if (filePath.endsWith(".mine")) {
			return convertWorld(file, "Classic");
		}
		else if (filePath.endsWith(".dat")) {
			Data.version.sendToLog(Helper.LogType.INFO, "Checking Selected World...");
			// We need to check if the file is a classic save before sending it to convertWorld(file);!
			return convertWorld(file, "Classic (.dat)");
		}
		else return null;
	}
	public static File convertWorld(File file, String type) {
		// We need to put our converter code here!
		Data.version.sendToLog(Helper.LogType.INFO, "Converting World...");
		ClientData.minecraft.m_6408915(new InfoScreen("Loading World", "Converting " + type + " world to Indev format", InfoScreen.Type.DIRT, false));
		return null;
	}
}
