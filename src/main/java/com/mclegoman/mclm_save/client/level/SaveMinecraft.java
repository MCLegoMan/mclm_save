package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.WorldChunk;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;

public class SaveMinecraft {
	public static SaveWorld currentWorld;
	public static long ticks;
	public static void tick(ModContainer mod) {
		try {
			if (currentWorld.getWorld() != null) {
				++ticks;
				if (ticks % SaveConfig.instance.autoSaveTicks.value() == 0L) currentWorld.save();
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst trying to autosave: " + error.getLocalizedMessage());
		}
	}
	public static File getMinecraftDir() {
		return QuiltLoader.getGameDir().toFile();
	}
	public static void loadWorld(String id) {
		currentWorld = null;
		ClientData.minecraft.m_9890357(null);
		System.gc();
		currentWorld = new SaveWorld.Builder(id).build();
		ClientData.minecraft.m_9890357(currentWorld.getWorld());
	}
	public static void saveWorldChunk(WorldChunk chunk, NbtCompound nbtCompound) {
		nbtCompound.putInt("xPos", chunk.chunkX);
		nbtCompound.putInt("zPos", chunk.chunkZ);
		nbtCompound.putLong("LastUpdate", currentWorld.getWorld().ticks);
		nbtCompound.putByteArray("Blocks", chunk.blockIds);
		nbtCompound.putByteArray("Data", chunk.blockMetadata.data);
		nbtCompound.putByteArray("SkyLight", chunk.skyLight.data);
		nbtCompound.putByteArray("BlockLight", chunk.blockLight.data);
		nbtCompound.putByteArray("HeightMap", chunk.heightMap);
		NbtList tileEntities = new NbtList();

		for (Object o : chunk.blockEntities.values()) {
			BlockEntity blockEntity = (BlockEntity) o;
			NbtCompound blockEntityData = new NbtCompound();
			String id = SaveEntity.BlockEntity.getId(blockEntity.getClass());
			blockEntityData.putString("id", id);
			blockEntityData.putInt("x", blockEntity.x);
			blockEntityData.putInt("y", blockEntity.y);
			blockEntityData.putInt("z", blockEntity.z);
			if (id.equals("Chest")) blockEntityData.put("Items", getInventoryData(((ChestBlockEntity) blockEntity).inventory));
			else if (id.equals("Furnace")) {
				blockEntityData.putShort("BurnTime", (short)((FurnaceBlockEntity) blockEntity).fuelTime);
				blockEntityData.putShort("CookTime", (short)((FurnaceBlockEntity) blockEntity).cookTime);
				blockEntityData.put("Items", getInventoryData(((FurnaceBlockEntity) blockEntity).inventory));
			} else Data.version.sendToLog(Helper.LogType.WARN, "Could not save Tile Entity at x:" + blockEntity.x + "y:" + blockEntity.y + "z:" + blockEntity.z + ": Unknown Tile Entity Type.");
			tileEntities.add(blockEntityData);
		}

		nbtCompound.put("TileEntities", tileEntities);
	}
	public static NbtList getInventoryData(ItemStack[] inventory) {
		return getInventoryData(new NbtList(), inventory);
	}
	public static NbtList getInventoryData(NbtList inventoryData, ItemStack[] inventory) {
		for(int item = 0; item < inventory.length; ++item) {
			if (inventory[item] != null) {
				NbtCompound itemData = new NbtCompound();
				itemData.putByte("Slot", (byte)item);
				itemData.putShort("id", (short)inventory[item].itemId);
				itemData.putByte("Count", (byte)inventory[item].size);
				itemData.putShort("Damage", (short)inventory[item].metadata);
				inventoryData.add(itemData);
			}
		}
		return inventoryData;
	}
}
