package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.chunk.WorldChunk;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class SaveChunkCache implements ChunkSource {
	private final ChunkSource generator;
	private final WorldChunk[] chunks = new WorldChunk[1024];

	public SaveChunkCache(ChunkSource chunkSource) {
		this.generator = chunkSource;
	}

	public boolean hasChunk(int i, int j) {
		int var3 = i & 31 | (j & 31) << 5;
		if (this.chunks[var3] != null) {
			WorldChunk chunk = this.chunks[var3];
			var3 = j;
			j = i;
			return j == chunk.chunkX && var3 == chunk.chunkZ;
		}

		return false;
	}

	private void saveChunk(WorldChunk worldChunk) {
		if (worldChunk != null) {
			File var2;
			if ((var2 = this.m_0435515(worldChunk.chunkX, worldChunk.chunkZ)).exists()) {
				SaveMinecraft.currentWorld.sizeOnDisk -= var2.length();
			}

			try {
				FileOutputStream var3 = new FileOutputStream(var2);
				NbtCompound var4 = new NbtCompound();
				NbtCompound var5 = new NbtCompound();
				var4.put("Level", var5);
				SaveMinecraft.saveWorldChunk(worldChunk, var5);
				SaveC_0877775.outputNbt(var4, var3);
				SaveMinecraft.currentWorld.sizeOnDisk += var2.length();
			} catch (Exception var6) {
				var6.printStackTrace();
			}
		}
	}

	public WorldChunk getChunk(int i, int j) {
		int var3 = i & 31 | (j & 31) << 5;
		if (!this.hasChunk(i, j)) {
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_1033437();
				this.saveChunk(this.chunks[var3]);
			}

			WorldChunk var4;
			if ((var4 = this.loadChunk(i, j)) == null) {
				var4 = this.generator.getChunk(i, j);
				this.saveChunk(var4);
			}

			this.chunks[var3] = var4;
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_6736297();
			}

			if (this.hasChunk(i + 1, j + 1) && this.hasChunk(i, j + 1) && this.hasChunk(i + 1, j)) {
				this.populateChunk(this, i, j);
			}

			if (this.hasChunk(i - 1, j + 1) && this.hasChunk(i, j + 1) && this.hasChunk(i - 1, j)) {
				this.populateChunk(this, i - 1, j);
			}

			if (this.hasChunk(i + 1, j - 1) && this.hasChunk(i, j - 1) && this.hasChunk(i + 1, j)) {
				this.populateChunk(this, i, j - 1);
			}

			if (this.hasChunk(i - 1, j - 1) && this.hasChunk(i, j - 1) && this.hasChunk(i - 1, j)) {
				this.populateChunk(this, i - 1, j - 1);
			}
		}

		return this.chunks[var3];
	}

	private File m_0435515(int x, int y) {
		String chunk = "c." + Integer.toString(x, 36) + "." + Integer.toString(y, 36) + ".dat";
		String x63 = Integer.toString(x & 63, 36);
		String y63 = Integer.toString(y & 63, 36);
		File dir;
		(dir = new File(SaveMinecraft.currentWorld.getDir(), x63)).mkdirs();
		(dir = new File(dir, y63)).mkdirs();
		return new File(dir, chunk);
	}

	private WorldChunk loadChunk(int x, int y) {
		File chunkFile = this.m_0435515(x, y);
		if (chunkFile.exists()) {
			try {
				NbtCompound chunkData = SaveC_0877775.m_1070817(Files.newInputStream(chunkFile.toPath()));
				return SaveChunk.m_2208916(chunkData.getCompound("Level"));
			} catch (Exception var3) {
				var3.printStackTrace();
			}
		}

		return null;
	}

	public void populateChunk(ChunkSource chunkSource, int i, int j) {
		this.generator.populateChunk(chunkSource, i, j);
	}

	public void save(boolean bl) {
		int var2 = 0;

		for(int var3 = 0; var3 < this.chunks.length; ++var3) {
			if (this.chunks[var3] != null) {// && this.chunks[var3].dirty) {
				this.saveChunk(this.chunks[var3]);
				//this.chunks[var3].dirty = false;
				++var2;
				if (var2 == 10 && !bl) {
					return;
				}
			}
		}

	}
}