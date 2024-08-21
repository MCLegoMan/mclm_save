package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import net.minecraft.world.World;

import java.io.File;
import java.nio.file.Files;

public class SaveWorld {
	private final File savesDir;
	private final String id;
	private SaveWorld(File savesDir, String id) {
		this.savesDir = savesDir;
		this.id = id;
	}
	public static class Builder {
		private File savesDir;
		private final String id;
		public Builder(String id) {
			this.savesDir = new File(SaveMinecraft.getMinecraftDir(), "saves");
			this.id = id;
		}
		public Builder savesDir(File file) {
			this.savesDir = file;
			return this;
		}
		public SaveWorld build() {
			return new SaveWorld(this.savesDir, this.id);
		}
	}
	public World getWorld() {
		World loadedWorld = ClientData.minecraft.f_5854988;
		return loadedWorld != null ? loadedWorld : new World();
	}
	public File getSavesDir() {
		return this.savesDir;
	}
	public String getId() {
		return this.id;
	}
	public File getDir() {
		return new File(getSavesDir(), getId());
	}
	public void save() {
		getDir().mkdirs();
		File var2 = new File(getDir(), "level.dat");
		NbtCompound var3;
		var3 = new NbtCompound();//.putLong("RandomSeed", getWorld().seed);
		var3.putInt("SpawnX", (int) getWorld().spawnpointX);
		var3.putInt("SpawnY", (int) getWorld().spawnpointY);
		var3.putInt("SpawnZ", (int) getWorld().spawnpointZ);
		//var3.putLong("Time", getWorld().ticks);
		//var3.putLong("SizeOnDisk", getWorld().sizeOnDisk);
		var3.putLong("LastPlayed", System.currentTimeMillis());
		NbtCompound var4;
		if (getWorld().f_6053391 != null) {
			var4 = new NbtCompound();
			((SaveEntity)getWorld().f_6053391).mclm_save$writeEntityNbt(var4);
			var3.putCompound("Player", var4);
		}

		(var4 = new NbtCompound()).put("Data", var3);

		try {
			SaveC_0877775.outputNbt(var4, Files.newOutputStream(var2.toPath()));
		} catch (Exception var5) {
			var5.printStackTrace();
		}

		((SaveChunkSource)getWorld().chunkSource).mclm_save$save(true);
	}
}
