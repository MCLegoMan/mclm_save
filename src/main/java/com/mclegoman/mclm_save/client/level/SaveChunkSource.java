package com.mclegoman.mclm_save.client.level;

import net.minecraft.world.chunk.WorldChunk;

import java.io.File;

public interface SaveChunkSource {
	void mclm_save$save(boolean bl);
	void mclm_save$saveChunk(WorldChunk worldChunk);
	File mclm_save$m_0435515(int i, int j);
}
