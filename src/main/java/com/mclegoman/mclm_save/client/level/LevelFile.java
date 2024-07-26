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
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.util.Translation;
import com.mclegoman.releasetypeutils.common.version.Helper;
import me.bluecrab2.classicexplorer.fields.*;
import me.bluecrab2.classicexplorer.fields.Class;
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
	public static SaveLoadScreen dialog;
	public static void load(boolean isLoad) {
		try {
			dialog = new SaveLoadScreen(isLoad);
			dialog.start();
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
									status.add("Please keep your classic save backed up!");
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
							new World().save(ClientData.minecraft.f_5854988, file);
						} catch (Exception error) {
							ClientData.minecraft.m_6408915(new InfoScreen("Error: Failed to save world!", error.getLocalizedMessage(), InfoScreen.Type.ERROR, true));
						} finally {
							if (SaveConfig.instance.saveBlockItems.value()) ClientData.minecraft.m_6408915(new InfoScreen("Saving World", "For vanilla compatibility, you will first need to load and save this world using Save for in-20100110.", InfoScreen.Type.DIRT, true));
							else ClientData.minecraft.m_6408915(null);
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
			ClientData.minecraft.m_6408915(new InfoScreen("Loading World", "Converting Classic (.mine) world to Indev format", InfoScreen.Type.DIRT, false));
			return convertWorld(file, ".mine");
		}
		else if (filePath.endsWith(".dat")) {
			ClientData.minecraft.m_6408915(new InfoScreen("Loading World", "Converting Generic Data File (.dat) world to Indev format", InfoScreen.Type.DIRT, false));
			return convertWorld(file, ".dat");
		}
		else {
			return new Couple(LoadOutputType.FAIL_INVALID_FORMAT, "Invalid file format!");
		}
	}
	public static Couple convertWorld(File file, String ext) {
		try {
			Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Loading File");
			String name = SaveConfig.instance.convertClassicDefaultName.value();
			String creator = SaveConfig.instance.convertClassicDefaultCreator.value();
			long createTime = Instant.now().getEpochSecond();
			int cloudColor = SaveConfig.instance.convertClassicDefaultCloudColor.value();
			short cloudHeight = SaveConfig.instance.convertClassicDefaultCloudHeight.value().shortValue();
			int fogColor = SaveConfig.instance.convertClassicDefaultFogColor.value();
			byte skyBrightness = SaveConfig.instance.convertClassicDefaultSkyBrightness.value().byteValue();
			int skyColor = SaveConfig.instance.convertClassicDefaultSkyColor.value();
			short surroundingGroundHeight = SaveConfig.instance.convertClassicDefaultSurroundingGroundHeight.value().shortValue();
			byte surroundingGroundType = SaveConfig.instance.convertClassicDefaultSurroundingGroundType.value().byteValue();
			short surroundingWaterHeight = SaveConfig.instance.convertClassicDefaultSurroundingWaterHeight.value().shortValue();
			byte surroundingWaterType = SaveConfig.instance.convertClassicDefaultSurroundingWaterType.value().byteValue();
			short spawnX = SaveConfig.instance.convertClassicDefaultSpawnX.value().shortValue();
			short spawnY = SaveConfig.instance.convertClassicDefaultSpawnY.value().shortValue();
			short spawnZ = SaveConfig.instance.convertClassicDefaultSpawnZ.value().shortValue();
			short height = SaveConfig.instance.convertClassicDefaultHeight.value().shortValue();
			short length = SaveConfig.instance.convertClassicDefaultLength.value().shortValue();
			short width = SaveConfig.instance.convertClassicDefaultWidth.value().shortValue();
			byte[] blocks = null;
			ClassField blockMap = null;
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
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					height = Short.parseShort(String.valueOf(field.getField())); // Was changed from "depth" to "height" in Indev.
					if (surroundingGroundHeight > height) surroundingGroundHeight = (short) ((height - 1) / 2);
					if (surroundingWaterHeight > height) surroundingWaterHeight = (short) (height - 1);
				}
				else if (field.getFieldName().equals("fogColor")) {
					fogColor = (int) field.getField();
				}
				else if (field.getFieldName().equals("height")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					length = Short.parseShort(String.valueOf(field.getField())); // Was changed from "height" to "length" in Indev.
				}
				else if (field.getFieldName().equals("skyColor")) {
					skyColor = (int) field.getField();
				}
				else if (field.getFieldName().equals("width")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					width = Short.parseShort(String.valueOf(field.getField()));
				}
				else if (field.getFieldName().equals("xSpawn")) {
					spawnX = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("ySpawn")) {
					spawnY = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("zSpawn")) {
					spawnZ = (short) (int) field.getField();
				}
				else if (field.getFieldName().equals("blocks")) {
					blocks = ((BlocksField)field).getBlocks();
				}
				else if (field.getFieldName().equals("creator")) {
					creator = (((ClassField)field).getString());
				}
				else if (field.getFieldName().equals("name")) {
					name = (((ClassField)field).getString());
				}
				else if (field.getFieldName().equals("blockMap")) {
					blockMap = ((ClassField) field);
				}
			}
			try {if (blocks != null) {
				if (blocks.length == (width * height * length)) {
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Writing File");
					TagCompound convertedLevel = createLevel(creator, createTime, name, cloudColor, cloudHeight, fogColor, skyBrightness, skyColor, surroundingGroundHeight, surroundingGroundType, surroundingWaterHeight, surroundingWaterType, spawnX, spawnY, spawnZ, height, length, width, blocks);
					if (SaveConfig.instance.convertClassicPlayer.value()) {
						TagList entities = new TagList();
						if (blockMap != null) {
							blockMap.getClassField().getFields().forEach((field) -> {
								if (field.getFieldName().equals("all")) {
									((ClassField)field).getArrayList().forEach(entityData -> {
										if (entityData.getName().equals("com.mojang.minecraft.player.Player")) {
											TagCompound data = new TagCompound();
											data.addNbt("id", new StringTag("LocalPlayer"));
											entityData.getFields().forEach(player -> {
												if (player.getFieldName().equals("inventory")) {
													TagList inventory = new TagList();
													ArrayList<Field> inventory1 = ((ClassField)player).getClassField().getFields();
													inventory1.forEach(invField -> {
														if (invField.getFieldName().equals("count")) {
															for (Field field2 : ((ArrayField)invField).getArray()) {
																TagCompound itemData = new TagCompound();
																byte count = ((Integer)field2.getField()).byteValue();
																itemData.addNbt("Count", new ByteTag(count));
																if (count != 0) inventory.addNbt(itemData);
															}
														} else if (invField.getFieldName().equals("slots")) {
															int index = 0;
															int slot = 0;
															for (Field field2 : ((ArrayField)invField).getArray()) {
																short id = ((Integer)field2.getField()).shortValue();
																if (id >= 0) {
																	TagCompound itemData = (TagCompound) inventory.getNbt(index);
																	itemData.addNbt("id", new ShortTag(id));
																	itemData.addNbt("Slot", new ByteTag(((Integer)slot).byteValue()));
																	index++;
																}
																slot++;
															}
														}
													});
													data.addNbt("Inventory", inventory);
												}
												if (player.getFieldName().equals("score")) {
													data.addNbt("Score", new IntTag((int) player.getField()));
												}
											});
											entityData.getSuperClass().getFields().forEach(mob -> {
											});
											TagList motion = new TagList();
											TagList pos = new TagList();
											TagList rotation = new TagList();
											entityData.getSuperClass().getSuperClass().getFields().forEach(entity -> {
												if (entity.getFieldName().equals("x") || entity.getFieldName().equals("y") || entity.getFieldName().equals("z")) {
													pos.addNbt(new FloatTag((float) entity.getField()));
												}
												if (entity.getFieldName().equals("fallDistance")) {
													data.addNbt("FallDistance", new FloatTag((float) entity.getField()));
												}
											});
											motion.addNbt(new FloatTag(0.0F));
											motion.addNbt(new FloatTag(-0.8F));
											motion.addNbt(new FloatTag(0.0F));
											rotation.addNbt(new FloatTag(0.0F));
											rotation.addNbt(new FloatTag(0.0F));
											data.addNbt("Motion", motion);
											data.addNbt("Pos", pos);
											data.addNbt("Rotation", rotation);
											data.addNbt("Fire", new ShortTag((short) -1));
											if (!data.isEmpty()) entities.addNbt(data);
										}
									});
								}
							});
						}
						convertedLevel.addNbt("Entities", entities);
					}
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
		TagCompound mclm_save = new TagCompound();
		// We add a custom section for mclm_save data.
		mclm_save.addNbt("GameVersion", new StringTag(Data.mcVersion));
		TagCompound ModVersion = new TagCompound();
		ModVersion.addNbt("name", new StringTag(Data.version.getName()));
		ModVersion.addNbt("id", new StringTag(Data.version.getID()));
		ModVersion.addNbt("major", new IntTag(Data.version.getMajor()));
		ModVersion.addNbt("minor", new IntTag(Data.version.getMinor()));
		ModVersion.addNbt("patch", new IntTag(Data.version.getPatch()));
		ModVersion.addNbt("type", new StringTag(Translation.releaseTypeString(Data.version.getType(), Translation.ReleaseTypeTranslationType.CODE)));
		ModVersion.addNbt("build", new IntTag(Data.version.getBuild()));
		if (Data.modContainer != null) ModVersion.addNbt("dirty", new StringTag(Data.modContainer.metadata().version().raw().contains("dirty") ? "true" : "false"));
		mclm_save.addNbt("ModVersion", ModVersion);
		Level.addNbt("mclm_save", mclm_save);
		return Level;
	}
	public enum LoadOutputType {
		SUCCESSFUL,
		SUCCESSFUL_CONVERT,
		FAIL_INVALID_FORMAT,
		FAIL_CONVERT
	}
}
