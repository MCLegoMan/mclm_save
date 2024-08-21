package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.SaveC_0877775;
import com.mclegoman.mclm_save.client.level.SaveChunkSource;
import com.mclegoman.mclm_save.client.level.SaveMinecraft;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.FileOutputStream;

@Mixin(ChunkCache.class)
public abstract class ChunkCacheMixin implements SaveChunkSource {
	@Shadow private WorldChunk[] chunks;

	public void mclm_save$save(boolean bl) {
		int var2 = 0;
		for (WorldChunk chunk : this.chunks) {
			if (chunk != null) {
				this.mclm_save$saveChunk(chunk);
				++var2;
				if (var2 == 10 && !bl) return;
			}
		}
	}

	public File mclm_save$m_0435515(int i, int j) {
		String chunkI = Integer.toString(i, 36); // TODO: Fix this.
		String chunkJ = Integer.toString(j, 36); // TODO: Fix this.

		String chunk = "c." + chunkI + "." + chunkJ + ".dat";
		String a = Integer.toString(i & 63, 36);
		String b = Integer.toString(j & 63, 36);
		File c;
		(c = new File(SaveMinecraft.currentWorld.getDir(), a)).mkdirs();
		(c = new File(c, b)).mkdirs();
		return new File(c, chunk);
	}

	public void mclm_save$saveChunk(WorldChunk worldChunk) {
		World world;
		File var2;
		if ((var2 = this.mclm_save$m_0435515(worldChunk.chunkX, worldChunk.chunkZ)).exists()) {
			world = SaveMinecraft.currentWorld.getWorld();
			//var10000.sizeOnDisk -= var2.length();
		}

		try {
			FileOutputStream var3 = new FileOutputStream(var2);
			NbtCompound var4 = new NbtCompound();
			NbtCompound var5 = new NbtCompound();
			var4.put("Level", var5);
			SaveMinecraft.saveWorldChunk(worldChunk, var5);
			SaveC_0877775.outputNbt(var4, var3);
			//var10000 = this.world;
			//var10000.sizeOnDisk += var2.length();
		} catch (Exception var6) {
			var6.printStackTrace();
		}
	}
}
