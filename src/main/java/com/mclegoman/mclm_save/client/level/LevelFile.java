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

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
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
		try (FileInputStream classicLevel = new FileInputStream(file)) {
			Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Loading File");
			DataInputStream inputStream = new DataInputStream(new GZIPInputStream(classicLevel));
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
			if (inputStream.readInt() == 656127880) {
				Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Checking Version");
				byte version = inputStream.readByte();
				if (version == 1) {
					try {
						Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Reading Classic:v1 Level");
						name = inputStream.readUTF();
						creator = inputStream.readUTF();
						createTime = inputStream.readLong();
						width = inputStream.readShort();
						length = inputStream.readShort();
						height = inputStream.readShort();
						blocks = new byte[width * length * height];
						inputStream.readFully(blocks);
					} catch (Exception error) {
						return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to convert Classic:v1 Level! " + error.getLocalizedMessage());
					}
				} else if (version == 2) {
					try {
						Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Reading Classic:v2 Level");
						// TODO: Version 2 Converter
						// If we get permission from bluecrab2 to include Classic Explorer code, we can use this to convert the world.
						//Settings.skipBlocks = false;
						//Class classicSave =  Reader.read(file);
						//ArrayList<Field> fields = classicSave.getFields();
						//for (int a = 0; a < fields.size(); a++) {
						//	if (fields.get(a).getFieldName().equals("width")) {
						//		width = (short) (int) fields.get(a).getField();
						//	}
						//	if (fields.get(a).getFieldName().equals("depth")) {
						//		height = (short) (int) fields.get(a).getField();
						//	}
						//	if (fields.get(a).getFieldName().equals("height")) {
						//		length = (short) (int) fields.get(a).getField();
						//	}
						//	if (fields.get(a).getFieldName().equals("blocks")) {
						//		ArrayList<Field> blockArray = ((ArrayField) fields.get(a)).getArray();
						//		byte[] blockArray2 = new byte[width * height * length];
						//		for (int b = 0; b < blockArray.size(); b++) {
						//			blockArray2[b] = (byte) blockArray.get(b).getField();
						//		}
						//		blocks = blockArray2;
						//	}
						//	Data.version.sendToLog(Helper.LogType.INFO, fields.get(a).getFieldName() + ":" + fields.get(a).getField());
						//}
						//int i;
						//while ((i = inputStream.read()) != -1) {}
						if (inputStream.readUnsignedShort() == 44269) {
							if (inputStream.readUnsignedShort() == 5) {
								int obj1 = inputStream.readUnsignedByte();
								if (obj1 == 115) {
									int obj2 = inputStream.readUnsignedByte();
									if (obj2 == 114) {
										if (inputStream.readUTF().equals("com.mojang.minecraft.level.Level")) {
											long serialVersionUID = inputStream.readLong();
											List<List<Object>> data = new ArrayList<>();
											int obj3 = inputStream.readUnsignedByte();
											if (obj3 == 2) {
												short varAmount = inputStream.readShort();
												for (int var = 0; var < varAmount - 1;) {
													char dataType = (char)inputStream.readByte();
													String dataName = inputStream.readUTF();
													if (dataType == 'B' || dataType == 'C' || dataType == 'D' || dataType == 'F' || dataType == 'I' || dataType == 'J' || dataType == 'S' || dataType == 'Z' || dataType == '[' || dataType == 'L') {
														List<Object> registry = new ArrayList<>();
														registry.add(dataName);
														registry.add(dataType);
														if (dataType == '[' || dataType == 'L') {
															if (inputStream.readUnsignedByte() == 116) registry.add(inputStream.readUTF());
														}
														data.add(registry);
														var++;
													} else {
														return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to convert Classic:v2 Level! Unexpected Data Type Found " + dataType + "!");
													}
												}
												// There is more data to be read here, what is it?

												byte endByte = inputStream.readByte();
												if (endByte == 120) {
													for (List<Object> registry : data) {
														if (registry.get(1).equals('B')) {
															inputStream.readByte();
														} else if (registry.get(1).equals('C')) {
															inputStream.readChar();
														} else if (registry.get(1).equals('D')) {
															inputStream.readDouble();
														} else if (registry.get(1).equals('F')) {
															inputStream.readFloat();
														} else if (registry.get(1).equals('I')) {
															int outInt = inputStream.readInt();
															if (((String)registry.get(0)).equalsIgnoreCase("depth")) {
																// depth was renamed to height in indev.
																height = (short)outInt;
																Data.version.sendToLog(Helper.LogType.INFO, "DEBUG: height: " + height);
															} else if (((String)registry.get(0)).equalsIgnoreCase("height")) {
																// height was renamed to length in indev.
																length = (short)outInt;
																Data.version.sendToLog(Helper.LogType.INFO, "DEBUG: length: " + length);
															} else if (((String)registry.get(0)).equalsIgnoreCase("width")) {
																width = (short)outInt;
																Data.version.sendToLog(Helper.LogType.INFO, "DEBUG: width: " + width);
															}
														} else if (registry.get(1).equals('J')) {
															inputStream.readLong();
														} else if (registry.get(1).equals('S')) {
															inputStream.readShort();
														} else if (registry.get(1).equals('Z')) {
															inputStream.readBoolean();
														} else if (registry.get(1).equals('[')) {
															String dataType = (String)registry.get(2);
														} else if (registry.get(1).equals('L')) {
															if (registry.size() > 2) {
																String dataType = (String)registry.get(2);
																dataType = dataType.substring(1, dataType.length() - 1);
																if (dataType.equals("java/lang/String")) {
																}
															} else {
															}
														}
														// There won't be any other types of data, so it's useless us checking for them.
													}
												} else {
													return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to convert Classic:v2 Level! Expected 120, got " + endByte + "!");
												}
											}
										}
									}
								}
							}
						}
						//return new Couple(LoadOutputType.FAIL_CONVERT, "Converting Classic:v2 worlds is not yet supported!");
					} catch (Exception error) {
						return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to convert Classic:v2 Level! " + error.getLocalizedMessage());
					}
				}
			} else {
				try {
					// Pre-Classic/Early Classic didn't have a magic number, so we just hope that this is a Pre-Classic or Early Classic file.
					length = 257;
					width = 257;
					blocks = new byte[width * length * height];
					inputStream.readFully(blocks);
				} catch (Exception error) {
					return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to convert Classic:v0 Level! " + error.getLocalizedMessage());
				}
			}
			// If there is any other data left, we fail the conversion.
			if (inputStream.read() != -1) {
				return new Couple(LoadOutputType.FAIL_CONVERT_UNEXPECTED_DATA, "World conversion failed due to unexpected data!");
			}
			try {
				if (blocks != null) {
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
					Data.version.sendToLog(Helper.LogType.INFO, "Converting World: Successfully converted world and saved at: " + outputPath);
					LevelFile.file = new File(outputPath);
					shouldLoad = new Couple(true, false);
					return new Couple(LoadOutputType.SUCCESSFUL_CONVERT, "Successfully converted world!");
				}
			} catch (Exception error) {
				return new Couple(LoadOutputType.FAIL_CONVERT, "Failed to write to file! " + error.getLocalizedMessage());
			}
		} catch (Exception error) {
			return new Couple(LoadOutputType.FAIL_CONVERT, error.getLocalizedMessage());
		}
		return new Couple(LoadOutputType.FAIL_CONVERT_UNKNOWN, "World conversion failed due to unknown causes!");
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
		FAIL_CONVERT,
		FAIL_CONVERT_UNEXPECTED_DATA,
		FAIL_CONVERT_UNKNOWN
	}
}
