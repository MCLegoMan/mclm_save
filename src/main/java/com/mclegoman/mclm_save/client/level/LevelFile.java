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
import com.mclegoman.mclm_save.client.tag.*;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;

import java.io.*;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
	public static void processWorld(File file) {
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
				if (!file.getPath().toLowerCase().endsWith(".mclevel")) file = new File(file.getPath() + ".mclevel");
				Data.version.sendToLog(Helper.LogType.INFO, "Saving World...");
				try {
					(new World()).save(ClientData.minecraft.f_5854988, file);
				} catch (Exception error) {
					Data.version.sendToLog(Helper.LogType.INFO, "Failed to save world!");
					ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to save world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
				} finally {
					ClientData.minecraft.m_6408915(null);
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
			return convertWorld(file, "Classic", ".mine");
		}
		else if (filePath.endsWith(".dat")) {
			return convertWorld(file, "Generic Data File", ".dat");
		}
		else return null;
	}
	public static File convertWorld(File file, String type, String ext) {
		Data.version.sendToLog(Helper.LogType.INFO, "Converting World...");
		ClientData.minecraft.m_6408915(new InfoScreen("Loading World", "Converting " + type + " (" + ext + ") world to Indev format", InfoScreen.Type.DIRT, false));
		// This is where the converter would go, after saving the saved file would be returned for loading.
		try (FileInputStream classicLevel = new FileInputStream(file)) {
			Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Loading File");
			DataInputStream inputStream = new DataInputStream(new GZIPInputStream(classicLevel));
			String name;
			String creator;
			long createTime;
			short width = 256;
			short depth = 64;
			short height = 256;
			byte[] blocks = null;
			if (inputStream.readInt() == 656127880) {
				Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Reading File");
				byte in1 = inputStream.readByte();
				if (in1 == 1) {
					// Version 1
					width = inputStream.readShort();
					depth = inputStream.readShort();
					height = inputStream.readShort();
					blocks = new byte[width * depth * height];
					inputStream.readFully(blocks);
					inputStream.close();
				} else if (in1 == 2) {
					// Version 2
					// Serialized Java...
				}
				if (blocks != null) {
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Writing File");
					TagCompound convertedLevel = createLevel(0xFFFFFF, (short) 66, 0xFFFFFF, (byte) 100, 10079487, (short) 23, (byte) 2, (short) 32, (byte) 8, (short) 128, (short) 36, (short) 128, height, depth, width, blocks);
					try (FileOutputStream outputStream = new FileOutputStream(file.getPath() + ".mclevel")) {
						GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
						Tag.output(convertedLevel, new DataOutputStream(gzipOutputStream));
						gzipOutputStream.close();
					} catch (Exception error) {
						Data.version.sendToLog(Helper.LogType.WARN, error.getLocalizedMessage());
					}
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Successfully converted world!");
					return new File(file.getPath() + ".mclevel");
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.INFO, "Failed to convert world!");
		}
		return null;
	}
	public static TagCompound createLevel(int cloudColor, short cloudHeight, int fogColor, byte skyBrightness, int skyColor, short surroundingGroundHeight, byte surroundingGroundType, short surroundingWaterHeight, byte surroundingWaterType, short spawnX, short spawnY, short spawnZ, short height, short length, short width, byte[] blocks) {
		TagCompound Level = new TagCompound();
		TagCompound Environment = new TagCompound();
		Environment.addNbt("CloudColor", new IntTag(cloudColor));
		Environment.addNbt("CloudHeight", new ShortTag(cloudHeight));
		Environment.addNbt("FogColor", new IntTag(fogColor));
		Environment.addNbt("SkyBrightness", new ByteTag(skyBrightness));
		Environment.addNbt("SkyColor", new IntTag(skyColor));
		Environment.addNbt("SurroundingGroundHeight", new ShortTag(surroundingGroundHeight));
		Environment.addNbt("SurroundingGroundType", new ByteTag(surroundingGroundType));
		Environment.addNbt("SurroundingWaterHeight", new ShortTag(surroundingWaterHeight));
		Environment.addNbt("SurroundingWaterType", new ByteTag(surroundingWaterType));
		Level.addNbt("Environment", Environment);
		TagCompound Map = new TagCompound();
		TagList Spawn = new TagList();
		Spawn.addNbt(new ShortTag(spawnX));
		Spawn.addNbt(new ShortTag(spawnY));
		Spawn.addNbt(new ShortTag(spawnZ));
		Map.addNbt("Spawn", Spawn);
		Map.addNbt("Height", new ShortTag(height));
		Map.addNbt("Length", new ShortTag(length));
		Map.addNbt("Width", new ShortTag(width));
		if (blocks != null) Map.addNbt("Blocks", new ByteArrayTag(blocks));
		Level.addNbt("Map", Map);
		return Level;
	}
}
