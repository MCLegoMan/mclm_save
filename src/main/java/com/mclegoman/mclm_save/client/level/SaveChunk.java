package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import net.minecraft.world.chunk.ChunkNibbleStorage;
import net.minecraft.world.chunk.WorldChunk;

public class SaveChunk {
	public static WorldChunk m_2208916(NbtCompound nbtCompound) {
		int var2 = nbtCompound.getInt("xPos");
		int var3 = nbtCompound.getInt("zPos");
		WorldChunk worldChunk = new WorldChunk(SaveMinecraft.currentWorld.getWorld(), nbtCompound.getByteArray("Blocks"), var2, var3);

		ChunkNibbleStorage blockMetadata = new ChunkNibbleStorage(0);
		blockMetadata.data = nbtCompound.getByteArray("Data");
		worldChunk.blockMetadata = blockMetadata;

		ChunkNibbleStorage skyLight = new ChunkNibbleStorage(0);
		skyLight.data = nbtCompound.getByteArray("SkyLight");
		worldChunk.skyLight = skyLight;

		ChunkNibbleStorage blockLight = new ChunkNibbleStorage(0);
		blockLight.data = nbtCompound.getByteArray("BlockLight");
		worldChunk.blockLight = blockLight;

		worldChunk.heightMap = nbtCompound.getByteArray("HeightMap");
		if (worldChunk.blockMetadata.data == null) {
			worldChunk.blockMetadata = new ChunkNibbleStorage(worldChunk.blockIds.length);
		}

		if (worldChunk.heightMap == null || worldChunk.skyLight.data == null) {
			worldChunk.heightMap = new byte[256];
			worldChunk.skyLight = new ChunkNibbleStorage(worldChunk.blockIds.length);
			worldChunk.populateSkylight();
		}

		if (worldChunk.blockLight.data == null) {
			worldChunk.blockLight = new ChunkNibbleStorage(worldChunk.blockIds.length);
		}

//		NbtList tileEntities;
//		if ((tileEntities = nbtCompound.getList("TileEntities")) != null) {
//			for(var2 = 0; var2 < tileEntities.size(); ++var2) {
//				BlockEntity var10;
//				if ((var10 = BlockEntity.fromNbt((NbtCompound)nbtCompound.get(var2))) != null) {
//					int var5 = var10.x - (worldChunk.chunkX << 4);
//					int var6 = var10.y;
//					int var7 = var10.z - (worldChunk.chunkZ << 4);
//					worldChunk.setBlockEntityAt(var5, var6, var7, var10);
//				}
//			}
//		}

		return worldChunk;
	}
}
