/*
    mclm_debug
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_debug
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.SaveConfig;
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
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow private RenderChunk[] compiledChunks;

	@Shadow protected abstract void updateRenderBoundaries(int i, int j, int k);

	@Shadow private World world;

	@Shadow private RenderChunk[] chunkStorage;

	@Shadow private int chunkGridSizeY;

	@Shadow private int chunkGridSizeX;

	@Shadow private List pendingChunks;

	@Shadow private int chunkGridSizeZ;

	@Shadow private boolean glArbOcclusion;

	@Shadow private int chunkGridMinX;

	@Shadow private int chunkGridMinY;

	@Shadow private int chunkGridMinZ;

	@Shadow private int chunkGridMaxY;

	@Shadow private int chunkGridMaxX;

	@Shadow private int chunkGridMaxZ;

	@Shadow private int viewDistance;

	@Shadow private C_5664496 minecraft;

	@Shadow private int translucentGlList;

	@Shadow private IntBuffer arbOcclusionBuffer;

	@Shadow protected abstract int render(int i, int j, int k, double d);

	@Shadow protected abstract void queryGlArbOcclusion(int i, int j);

	@Shadow private double cameraZ;

	@Shadow private double cameraY;

	@Shadow private double cameraX;

	@Shadow private int compiledChunkCount;

	@Shadow private int glArbOccludedChunkCount;

	@Shadow private int invisibleChunkCount;

	@Shadow private int chunkCount;

	@Shadow protected abstract void m_6748042();

	@Shadow private int ticks;

	@Inject(method = "m_6748042", at = @At(value = "HEAD"), cancellable = true)
	private void mclm_save$m_6748042(CallbackInfo ci) {
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
			if (SaveConfig.instance.logErrorCatching.value()) Data.version.sendToLog(Helper.LogType.WARN, "An error occourred whilst executing WorldRenderer/m_6748042: " + error.getLocalizedMessage());
		}
		ci.cancel();
	}
	@Inject(method = "render(Lnet/minecraft/entity/living/player/PlayerEntity;ID)I", at = @At(value = "HEAD"), cancellable = true)
	private void mclm_save$render(PlayerEntity playerEntity, int i, double d, CallbackInfoReturnable<Integer> cir) {
		try {
			if (this.minecraft.f_9967940.f_7110074 != this.viewDistance) {
				this.m_6748042();
			}

			if (i == 0) {
				this.chunkCount = 0;
				this.invisibleChunkCount = 0;
				this.glArbOccludedChunkCount = 0;
				this.compiledChunkCount = 0;
			}

			double var5 = playerEntity.prevTickX + (playerEntity.x - playerEntity.prevTickX) * d;
			double var7 = playerEntity.prevTickY + (playerEntity.y - playerEntity.prevTickY) * d;
			double var9 = playerEntity.prevTickZ + (playerEntity.z - playerEntity.prevTickZ) * d;
			double var11 = playerEntity.x - this.cameraX;
			double var13 = playerEntity.y - this.cameraY;
			double var15 = playerEntity.z - this.cameraZ;
			if (var11 * var11 + var13 * var13 + var15 * var15 > 16.0) {
				this.cameraX = playerEntity.x;
				this.cameraY = playerEntity.y;
				this.cameraZ = playerEntity.z;
				this.updateRenderBoundaries(MathHelper.floor(playerEntity.x), MathHelper.floor(playerEntity.y), MathHelper.floor(playerEntity.z));
				Arrays.sort(this.compiledChunks, new CompiledChunkComparator(playerEntity));
			}

			int var21;
			if (this.glArbOcclusion && !this.minecraft.f_9967940.f_5010661 && i == 0) {
				int var22 = 16;
				this.queryGlArbOcclusion(0, 16);

				for(int var14 = 0; var14 < 16; ++var14) {
					this.compiledChunks[var14].glArbOcclusion = true;
				}

				var21 = 0 + this.render(0, 16, i, d);

				do {
					int var12 = var22;
					if ((var22 <<= 1) > this.compiledChunks.length) {
						var22 = this.compiledChunks.length;
					}

					GL11.glDisable(3553);
					GL11.glDisable(2896);
					GL11.glDisable(3008);
					GL11.glDisable(2912);
					GL11.glColorMask(false, false, false, false);
					GL11.glDepthMask(false);
					this.queryGlArbOcclusion(var12, var22);
					GL11.glPushMatrix();
					float var23 = 0.0F;
					float var24 = 0.0F;
					float var16 = 0.0F;

					for(int var17 = var12; var17 < var22; ++var17) {
						if (this.compiledChunks[var17].isEmpty()) {
							this.compiledChunks[var17].visible = false;
						} else {
							if (!this.compiledChunks[var17].visible) {
								this.compiledChunks[var17].glArbOcclusion = true;
							}

							if (this.compiledChunks[var17].visible && !this.compiledChunks[var17].glArbOcclusionPending) {
								float var18 = MathHelper.sqrt(this.compiledChunks[var17].compare(playerEntity));
								int var25 = (int)(1.0F + var18 / 64.0F);
								if (this.ticks % var25 == var17 % var25) {
									RenderChunk var26;
									float var19 = (float)((double)(var26 = this.compiledChunks[var17]).regionOriginX - var5);
									float var20 = (float)((double)var26.regionOriginY - var7);
									var18 = (float)((double)var26.regionOriginZ - var9);
									var19 -= var23;
									var20 -= var24;
									var18 -= var16;
									if (var19 != 0.0F || var20 != 0.0F || var18 != 0.0F) {
										GL11.glTranslatef(var19, var20, var18);
										var23 += var19;
										var24 += var20;
										var16 += var18;
									}

									ARBOcclusionQuery.glBeginQueryARB(35092, this.compiledChunks[var17].glArbOcclusionQuery);
									this.compiledChunks[var17].glCallList();
									ARBOcclusionQuery.glEndQueryARB(35092);
									this.compiledChunks[var17].glArbOcclusionPending = true;
								}
							}
						}
					}

					GL11.glPopMatrix();
					GL11.glColorMask(true, true, true, true);
					GL11.glDepthMask(true);
					GL11.glEnable(3553);
					GL11.glEnable(3008);
					GL11.glEnable(2912);
					var21 += this.render(var12, var22, i, d);
				} while(var22 < this.compiledChunks.length);
			} else {
				var21 = 0 + this.render(0, this.compiledChunks.length, i, d);
			}

			cir.setReturnValue(var21);
		} catch (Exception error) {
			if (SaveConfig.instance.logErrorCatching.value()) Data.version.sendToLog(Helper.LogType.WARN, "An error occourred whilst executing WorldRenderer/render: " + error.getLocalizedMessage());
		}
		cir.cancel();
	}
	@Inject(method = "m_8580944", at = @At(value = "HEAD"), cancellable = true)
	private void mclm_save$m_8580944(PlayerEntity playerEntity, CallbackInfo ci) {
		try {
			this.pendingChunks.sort(new PendingChunkComparator(playerEntity));
			int var2 = this.pendingChunks.size() - 1;
			int var3 = this.pendingChunks.size();

			for(int var4 = 0; var4 < var3; ++var4) {
				RenderChunk var5;
				if ((var5 = (RenderChunk)this.pendingChunks.get(var2 - var4)).compare(playerEntity) > 2500.0F && var4 > 2) {
					return;
				}

				this.pendingChunks.remove(var5);
				var5.compile();
				var5.dirty = false;
			}
		} catch (Exception error) {
			if (SaveConfig.instance.logErrorCatching.value()) Data.version.sendToLog(Helper.LogType.WARN, "An error occourred whilst executing WorldRenderer/m_8580944: " + error.getLocalizedMessage());
		}
		ci.cancel();
	}
}