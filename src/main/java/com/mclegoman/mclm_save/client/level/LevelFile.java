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
		try (FileInputStream classicLevel = new FileInputStream(file)) {
			Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Loading File");
			DataInputStream inputStream = new DataInputStream(new GZIPInputStream(classicLevel));
			int cloudColor = 0xFFFFFF;
			short cloudHeight = (short) 66;
			int fogColor = 0xFFFFFF;
			byte skyBrightness = (byte) 100;
			int skyColor = 10079487;
			short surroundingGroundHeight = (short) 23;
			byte surroundingGroundType = (byte) 2;
			short surroundingWaterHeight = (short) 32;
			byte surroundingWaterType = (byte) 8;
			short spawnX = (short) 128;
			short spawnY = (short) 36;
			short spawnZ = (short) 128;
			short height = 64;
			short length = 256;
			short width = 256;
			byte[] blocks = null;
			// Does this differ in versions?
			if (inputStream.readInt() == 656127880) {
				Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Checking Version");
				byte version = inputStream.readByte();
				if (version == 1) {
					try {
						Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Reading Classic:v1 Level");
						// We read the name, creator, and createTime, but we don't actually use them.
						String name = inputStream.readUTF();
						String creator = inputStream.readUTF();
						long createTime = inputStream.readLong();
						width = inputStream.readShort();
						length = inputStream.readShort();
						height = inputStream.readShort();
						blocks = new byte[width * length * height];
						inputStream.readFully(blocks);
						inputStream.close();
					} catch (Exception error) {
						Data.version.sendToLog(Helper.LogType.WARN, "Failed to convert Classic:v1 Level!");
					}
				} else if (version == 2) {
					try {
						Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Reading Classic:v2 Level");
						// TODO: Version 2 Converter
					} catch (Exception error) {
						Data.version.sendToLog(Helper.LogType.WARN, "Failed to convert Classic:v2 Level!");
					}
				}
			} else {
				try {
					// Pre-Classic/Early Classic didn't have a magic number, so we just hope that this is a Pre-Classic or Early Classic file.
					blocks = new byte[width * length * height];
					inputStream.readFully(blocks);
					inputStream.close();
				} catch (Exception error) {
					Data.version.sendToLog(Helper.LogType.WARN, "Failed to convert Classic:v0 Level!");
				}
			}
			try {
				if (blocks != null) {
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Writing File");
					TagCompound convertedLevel = createLevel(cloudColor, cloudHeight, fogColor, skyBrightness, skyColor, surroundingGroundHeight, surroundingGroundType, surroundingWaterHeight, surroundingWaterType, spawnX, spawnY, spawnZ, height, length, width, blocks);
					String outputPath = file.getPath().endsWith(ext) ? file.getPath().substring(0, file.getPath().length() - ext.length()) : file.getPath();
					String outputPathCheck = outputPath;
					int fileAmount = 1;
					while (new File(outputPathCheck + ".mclevel").exists()) {
						outputPathCheck = outputPath + "(" + fileAmount + ")";
						fileAmount++;
					}
					outputPath = outputPathCheck + ".mclevel";
					try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
						GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
						Tag.output(convertedLevel, new DataOutputStream(gzipOutputStream));
						gzipOutputStream.close();
					} catch (Exception error) {
						Data.version.sendToLog(Helper.LogType.WARN, error.getLocalizedMessage());
					}
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Successfully converted world and saved at: " + outputPath);
					return new File(outputPath);
				}
			} catch (Exception error) {
				Data.version.sendToLog(Helper.LogType.WARN, "Failed to write to file!");
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Failed to convert world!");
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
