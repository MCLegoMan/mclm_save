/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.gui.InfoScreen;
import com.mclegoman.mclm_save.client.gui.SaveLoadScreen;
import com.mclegoman.mclm_save.client.tag.*;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.common.util.Couple;
import com.mclegoman.releasetypeutils.common.version.Helper;
import me.bluecrab2.classicexplorer.fields.ArrayField;
import me.bluecrab2.classicexplorer.fields.Class;
import me.bluecrab2.classicexplorer.fields.Field;
import me.bluecrab2.classicexplorer.io.Reader;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public final class LevelFile {
	public static File file;
	public static boolean isLoad;
	public static boolean shouldProcess;
	public static Couple shouldLoad;
	public static void load(boolean isLoad) {
		try {
			SaveLoadScreen screen = new SaveLoadScreen(isLoad);
			screen.start();
		} finally {
			ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Please Wait", InfoScreen.Type.DIRT, false));
		}
	}
	public static void processWorld() {
		try {
			if (file != null) {
				ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Processing File", InfoScreen.Type.DIRT, false));
				if (file != null) {
					if (isLoad) {
						try {
							Couple worldStatus = setWorldFile(file);
							if (!(worldStatus.getFirst().equals(LoadOutputType.SUCCESSFUL) || worldStatus.getFirst().equals(LoadOutputType.SUCCESSFUL_CONVERT))) {
								ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", (String) worldStatus.getSecond(), InfoScreen.Type.ERROR, true));
							} else {
								if (worldStatus.getFirst().equals(LoadOutputType.SUCCESSFUL_CONVERT)) {
									List<String> status = new ArrayList<>();
									status.add("Saved at: " + file.getPath());
									status.add("");
									status.add("You may notice some issues relating to block positions.");
									status.add("Keep your classic save backed up!");
									ClientData.minecraft.m_6408915(new InfoScreen("Classic Conversion Successful!", status, InfoScreen.Type.DIRT, true));
								}
							}
						} catch (Exception error) {
							ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
						}
					} else {
						if (!file.getPath().toLowerCase().endsWith(".mclevel")) file = new File(file.getPath() + ".mclevel");
						Data.version.sendToLog(Helper.LogType.INFO, "Process World: Saving World...");
						try {
							(new World()).save(ClientData.minecraft.f_5854988, file);
						} catch (Exception error) {
							ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to save world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
						} finally {
							ClientData.minecraft.m_6408915(null);
						}
					}
				}
			}
		} catch (Exception error) {
			// We use InfoScreen instead of FatalErrorScreen so the player can use ESC to go back to the game.
			ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
		}
	}
	public static void loadWorld(boolean clearScreen) {
		try {
			if (file != null) {
				if (file.isFile() && file.exists()) {
					FileInputStream file1 = new FileInputStream(file);
					Data.version.sendToLog(Helper.LogType.INFO, "Process World: Loading World...");
					net.minecraft.world.World world = (new World()).load(file1);
					file1.close();
					Accessors.MinecraftClient.loadWorld(world);
				}
				if (clearScreen) ClientData.minecraft.m_6408915(null);
			} else {
				ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", "World does not exist.", InfoScreen.Type.ERROR, true));
			}
		} catch (Exception error) {
			ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to load world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
		}
	}
	public static Couple setWorldFile(File file) {
		String filePath = file.getPath().toLowerCase();
		if (filePath.endsWith(".mclevel")) {
			shouldLoad = new Couple(true, true);
			return new Couple(LoadOutputType.SUCCESSFUL, "Successfully loaded world!");
		}
		else if (filePath.endsWith(".mine")) {
			return convertWorld(file, "Classic", ".mine");
		}
		else if (filePath.endsWith(".dat")) {
			return convertWorld(file, "Generic Data File", ".dat");
		}
		else {
			return new Couple(LoadOutputType.FAIL_INVALID_FORMAT, "Invalid file format!");
		}
	}
	public static Couple convertWorld(File file, String type, String ext) {
		ClientData.minecraft.m_6408915(new InfoScreen("Loading World", "Converting " + type + " (" + ext + ") world to Indev format", InfoScreen.Type.DIRT, false));
		try {
			Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Loading File");
			String name = "A Nice World";
			String creator = "Player";
			long createTime = Instant.now().getEpochSecond();
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
			Class classicSave =  Reader.read(file);
			ArrayList<Field> fields = classicSave.getFields();
			for (Field field : fields) {
				// IntelliJ, if we weren't using Java 8, I would use a switch. But we're using it, so stop telling me to use a switch.
				if (field.getFieldName().equals("cloudColor")) {
					cloudColor = (int) field.getField();
				}
				else if (field.getFieldName().equals("createTime")) {
					createTime = (long) field.getField();
				}
				else if (field.getFieldName().equals("depth")) {
					height = (short) (int) field.getField(); // Was changed from "depth" to "height" in Indev.
				}
				else if (field.getFieldName().equals("fogColor")) {
					fogColor = (int) field.getField();
				}
				else if (field.getFieldName().equals("height")) {
					length = (short) (int) field.getField(); // Was changed from "height" to "length" in Indev.
				}
				else if (field.getFieldName().equals("skyColor")) {
					skyColor = (int) field.getField();
				}
				else if (field.getFieldName().equals("width")) {
					width = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("spawnX")) {
					spawnX = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("spawnY")) {
					spawnY = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("spawnZ")) {
					spawnZ = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("blocks")) {
					ArrayList<Field> blockArray = ((ArrayField) field).getArray();
					byte[] blockArray2 = new byte[width * height * length];
					for (int b = 0; b < blockArray.size(); b++) {
						blockArray2[b] = (byte) blockArray.get(b).getField();
					}
					blocks = blockArray2;
				}
				else if (field.getFieldName().equals("creator")) {
					creator = (String) field.getField();
				}
				else if (field.getFieldName().equals("name")) {
					name = (String) field.getField();
				}
			}
			try {if (blocks != null) {
				if (blocks.length == (width * height * length)) {
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Writing File");
					TagCompound convertedLevel = createLevel(creator, createTime, name, cloudColor, cloudHeight, fogColor, skyBrightness, skyColor, surroundingGroundHeight, surroundingGroundType, surroundingWaterHeight, surroundingWaterType, spawnX, spawnY, spawnZ, height, length, width, blocks);
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
						return new Couple(LoadOutputType.FAIL_CONVERT, error.getLocalizedMessage());
					}
					LevelFile.file = new File(outputPath);
					shouldLoad = new Couple(true, false);
					return new Couple(LoadOutputType.SUCCESSFUL_CONVERT, "Successfully converted world!");
				} else {
					return new Couple(LoadOutputType.FAIL_CONVERT, "World conversion failed due to block amount not matching!");
				}
			} else {
				return new Couple(LoadOutputType.FAIL_CONVERT, "World conversion failed due to blocks not existing!");
			}
			} catch (Exception error) {
				return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to write to file! " + error.getLocalizedMessage());
			}
		} catch (Exception error) {
			return new Couple(LoadOutputType.FAIL_CONVERT, error.getLocalizedMessage());
		}
	}
	public static TagCompound createLevel(String creator, long createTime, String name, int cloudColor, short cloudHeight, int fogColor, byte skyBrightness, int skyColor, short surroundingGroundHeight, byte surroundingGroundType, short surroundingWaterHeight, byte surroundingWaterType, short spawnX, short spawnY, short spawnZ, short height, short length, short width, byte[] blocks) {
		TagCompound Level = new TagCompound();
		TagCompound About = new TagCompound();
		About.addNbt("Author", new StringTag(creator));
		About.addNbt("CreatedOn", new LongTag(createTime));
		About.addNbt("Name", new StringTag(name));
		Level.addNbt("About", About);
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
	public enum LoadOutputType {
		SUCCESSFUL,
		SUCCESSFUL_CONVERT,
		FAIL_INVALID_FORMAT,
		FAIL_CONVERT
	}
}
