package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.world.World;

import java.io.File;
import java.nio.file.Files;

public class SaveWorldThread extends Thread {
	public SaveWorldThread() {
	}
	public void run() {
		SaveWorld saveWorld = SaveMinecraft.currentWorld;
		World world = saveWorld.getWorld();

		saveWorld.getDir().mkdirs();
		File var2 = new File(saveWorld.getDir(), "level.dat");
		NbtCompound levelData;
		levelData = new NbtCompound();
		levelData.putLong("RandomSeed", 0L);
		levelData.putInt("SpawnX", (int) world.spawnpointX);
		levelData.putInt("SpawnY", (int) world.spawnpointY);
		levelData.putInt("SpawnZ", (int) world.spawnpointZ);
		levelData.putLong("Time", world.ticks);
		//var3.putLong("SizeOnDisk", world.sizeOnDisk);
		levelData.putLong("LastPlayed", System.currentTimeMillis());
		NbtCompound playerData;
		if (world.m_0519267() != null && !world.m_0519267().removed) {
			playerData = new NbtCompound();
			InputPlayerEntity player = (InputPlayerEntity) world.m_0519267();
			playerData.putShort("Health", (short)player.health);
			playerData.putShort("HurtTime", (short)player.hurtTime);
			playerData.putShort("DeathTime", (short)player.deathTime);
			playerData.putShort("AttackTime", (short)player.attackTime);
			playerData.putInt("Score", player.playerScore);
			playerData.putString("id", "LocalPlayer");
			playerData.put("Pos", SaveEntity.toNbtList((double)player.x, (double)player.y, (double)player.z));
			playerData.put("Motion", SaveEntity.toNbtList((double)player.velocityX, (double)player.velocityY, (double)player.velocityZ));
			playerData.put("Rotation", SaveEntity.toNbtList((float)player.yaw, (float)player.pitch));
			playerData.putFloat("FallDistance", player.fallDistance);
			playerData.putShort("Fire", (short)player.onFireTimer);
			playerData.putShort("Air", (short)player.f_1297340);
			NbtList playerInventoryData = new NbtList();
			SaveMinecraft.getInventoryData(playerInventoryData, player.inventory.inventorySlots);
			SaveMinecraft.getInventoryData(playerInventoryData, player.inventory.armorSlots);
			playerData.put("Inventory", playerInventoryData);
			levelData.putCompound("Player", playerData);
		}

		NbtCompound level = new NbtCompound();
		level.put("Data", levelData);

		try {
			SaveC_0877775.outputNbt(level, Files.newOutputStream(var2.toPath()));
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst trying to save world: " + error.getLocalizedMessage());
		}
		((SaveChunkSource)world.chunkSource).mclm_save$save(true);
	}
}
