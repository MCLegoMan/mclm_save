/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.render.world.PendingChunkComparator;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow private List pendingChunks;

	@Inject(method = "m_8580944", at = @At("HEAD"), cancellable = true)
	private void save$error(PlayerEntity playerEntity, CallbackInfo ci) {
		// We override the m_8580944 function as when loading a big world,
		// it can cause the game to crash, this just makes it fail instead.
		try {
			this.pendingChunks.sort(new PendingChunkComparator(playerEntity));
			int i = this.pendingChunks.size() - 1;
			int var2;
			if ((var2 = this.pendingChunks.size()) > 3) {
				var2 = 3;
			}

			for(int var3 = 0; var3 < var2; ++var3) {
				RenderChunk var4;
				(var4 = (RenderChunk)this.pendingChunks.remove(i - var3)).compile();
				var4.dirty = false;
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occourred! (WorldRenderer/m_8580944): " + error);
		}
		ci.cancel();
	}
}
