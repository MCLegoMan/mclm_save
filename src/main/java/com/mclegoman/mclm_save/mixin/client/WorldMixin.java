package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.SaveChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;
import net.minecraft.world.chunk.ChunkSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow public ChunkSource chunkSource;

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void save$init(CallbackInfo ci) {
		chunkSource = new SaveChunkCache(((ChunkCache)chunkSource).generator);
	}
}
