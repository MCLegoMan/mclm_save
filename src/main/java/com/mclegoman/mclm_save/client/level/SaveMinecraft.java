package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;
import java.util.Iterator;

public class SaveMinecraft {
	public static SaveWorld currentWorld;
	public static File getMinecraftDir() {
		return QuiltLoader.getGameDir().toFile();
	}
	public static void loadWorld(String id) {
		currentWorld = null;
		ClientData.minecraft.m_9890357(null);
		System.gc();
		World world = new World();
		currentWorld = new SaveWorld.Builder(id).build();
		ClientData.minecraft.m_9890357(currentWorld.getWorld());
	}
	public static void saveWorldChunk(WorldChunk chunk, NbtCompound nbtCompound) {
		//((SaveWorldChunk)chunk).mclm_save$m_8402936(nbtCompound);
		nbtCompound.putInt("xPos", chunk.chunkX);
		nbtCompound.putInt("zPos", chunk.chunkZ);
		nbtCompound.putLong("LastUpdate", currentWorld.getWorld().ticks);
		nbtCompound.putByteArray("Blocks", chunk.blockIds);
		nbtCompound.putByteArray("Data", chunk.blockMetadata.data);
		nbtCompound.putByteArray("SkyLight", chunk.skyLight.data);
		nbtCompound.putByteArray("BlockLight", chunk.blockLight.data);
		nbtCompound.putByteArray("HeightMap", chunk.heightMap);
		NbtList var2 = new NbtList();
		Iterator var3 = chunk.blockEntities.values().iterator();

		while(var3.hasNext()) {
			BlockEntity var4 = (BlockEntity)var3.next();
			NbtCompound var5 = new NbtCompound();
			//var4.writeNbt(var5);
			var2.add(var5);
		}

		nbtCompound.put("TileEntities", var2);
	}
}
