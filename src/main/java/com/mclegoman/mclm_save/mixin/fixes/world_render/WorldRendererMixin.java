/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.world_render;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.C_5664496;
import net.minecraft.client.render.CompiledChunkComparator;
import net.minecraft.client.render.world.PendingChunkComparator;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow private List pendingChunks;
	@Shadow private RenderChunk[] compiledChunks;

	@Shadow protected abstract void updateRenderBoundaries(int i, int j, int k);

	@Shadow private World world;

	@Shadow private RenderChunk[] chunkStorage;

	@Shadow private int chunkGridSizeY;

	@Shadow private int chunkGridSizeX;

	@Shadow private boolean glArbOcclusion;

	@Shadow private int translucentGlList;

	@Shadow private IntBuffer arbOcclusionBuffer;

	@Shadow private int chunkGridSizeZ;

	@Shadow private int chunkGridMaxZ;

	@Shadow private int chunkGridMaxX;

	@Shadow private int chunkGridMaxY;

	@Shadow private int chunkGridMinZ;

	@Shadow private int chunkGridMinY;

	@Shadow private int chunkGridMinX;

	@Shadow private int viewDistance;

	@Shadow private C_5664496 minecraft;

	@Inject(method = "m_8580944", at = @At(value = "HEAD"), cancellable = true)
	public void save$m_8580944(PlayerEntity player, CallbackInfo ci) {
		try {
			Collections.sort(this.pendingChunks, new PendingChunkComparator(player));
			int var2 = this.pendingChunks.size() - 1;
			int var3 = this.pendingChunks.size();

			for(int var4 = 0; var4 < var3; ++var4) {
				RenderChunk var5;
				if ((var5 = (RenderChunk)this.pendingChunks.get(var2 - var4)).compare(player) > 2500.0F && var4 > 2) {
					return;
				}

				this.pendingChunks.remove(var5);
				var5.compile();
				var5.dirty = false;
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst sorting WorldRenderer/m_8580944: " + error.getLocalizedMessage());
		}
		ci.cancel();
	}
	@Inject(method = "m_6748042", at = @At(value = "HEAD"), cancellable = true)
	public void save$m_6748042(CallbackInfo ci) {
		try {
			this.viewDistance = this.minecraft.f_9967940.f_7110074;
			int var1;
			if (this.chunkStorage != null) {
				for(var1 = 0; var1 < this.chunkStorage.length; ++var1) {
					this.chunkStorage[var1].releaseBuffers();
				}
			}

			if ((var1 = 5 << 3 - this.viewDistance) > 28) {
				var1 = 28;
			}

			this.chunkGridSizeX = var1;
			this.chunkGridSizeY = 8;
			this.chunkGridSizeZ = var1;
			this.chunkStorage = new RenderChunk[this.chunkGridSizeX * this.chunkGridSizeY * this.chunkGridSizeZ];
			this.compiledChunks = new RenderChunk[this.chunkGridSizeX * this.chunkGridSizeY * this.chunkGridSizeZ];
			var1 = 0;
			int var2 = 0;
			this.chunkGridMinX = 0;
			this.chunkGridMinY = 0;
			this.chunkGridMinZ = 0;
			this.chunkGridMaxX = this.chunkGridSizeX;
			this.chunkGridMaxY = this.chunkGridSizeY;
			this.chunkGridMaxZ = this.chunkGridSizeZ;

			int var3;
			for(var3 = 0; var3 < this.pendingChunks.size(); ++var3) {
				((RenderChunk)this.pendingChunks.get(var3)).dirty = false;
			}

			this.pendingChunks.clear();

			for(var3 = 0; var3 < this.chunkGridSizeX; ++var3) {
				for(int var4 = 0; var4 < this.chunkGridSizeY; ++var4) {
					for(int var5 = 0; var5 < this.chunkGridSizeZ; ++var5) {
						this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3] = new RenderChunk(this.world, var3 << 4, var4 << 4, var5 << 4, 16, this.translucentGlList + var1);
						if (this.glArbOcclusion) {
							this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3].glArbOcclusionQuery = this.arbOcclusionBuffer.get(var2);
						}

						this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3].glArbOcclusionPending = false;
						this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3].glArbOcclusion = true;
						this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3].visible = true;
						++var2;
						this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3].dirty = true;
						this.compiledChunks[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3] = this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3];
						this.pendingChunks.add(this.chunkStorage[(var5 * this.chunkGridSizeY + var4) * this.chunkGridSizeX + var3]);
						var1 += 3;
					}
				}
			}

			Entity var6 = this.world.f_6053391;
			this.updateRenderBoundaries(MathHelper.floor(var6.x), MathHelper.floor(var6.y), MathHelper.floor(var6.z));
			Arrays.sort(this.compiledChunks, new CompiledChunkComparator(var6));
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst sorting WorldRenderer/m_6748042: " + error.getLocalizedMessage());
		}
		ci.cancel();
	}
}
