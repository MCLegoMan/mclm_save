/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.world_render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderer;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderChunk.class)
public abstract class RenderChunkMixin {
	@Shadow public boolean dirty;
	@Shadow public static int updateCounter;
	@Shadow private int originX;
	@Shadow private int originY;
	@Shadow private int originZ;
	@Shadow private int f_6592251;
	@Shadow private int f_8249186;
	@Shadow private int f_4676706;
	@Shadow private boolean[] blocks;
	@Shadow private int glList;
	@Shadow private int regionX;
	@Shadow private int regionY;
	@Shadow private int regionZ;
	@Shadow private static BufferBuilder BUFFER_BUILDER;
	@Shadow private World world;
	@Shadow private BlockRenderer f_3953567;
	@Shadow public boolean hasSkyLight;
	@Inject(method = "compile", at = @At(value = "HEAD"), cancellable = true)
	public void save$compile(CallbackInfo ci) {
		if (this.dirty) {
			++updateCounter;
			int var1 = this.originX;
			int var2 = this.originY;
			int var3 = this.originZ;
			int var4 = this.originX + this.f_6592251;
			int var5 = this.originY + this.f_8249186;
			int var6 = this.originZ + this.f_4676706;

			int var7;
			for(var7 = 0; var7 < 2; ++var7) {
				this.blocks[var7] = true;
			}

			WorldChunk.hasSkyLight = false;

			for(var7 = 0; var7 < 2; ++var7) {
				boolean var8 = false;
				boolean var9 = false;
				GL11.glNewList(this.glList + var7, 4864);
				GL11.glPushMatrix();
				GL11.glTranslatef((float)this.regionX, (float)this.regionY, (float)this.regionZ);
				BUFFER_BUILDER.start();
				BUFFER_BUILDER.offset(-this.originX, -this.originY, -this.originZ);

				for(int var10 = var2; var10 < var5; ++var10) {
					for(int var11 = var3; var11 < var6; ++var11) {
						for(int var12 = var1; var12 < var4; ++var12) {
							int var13;
							if ((var13 = this.world.m_9893076(var12, var10, var11)) > 0) {
								if (Block.BY_ID[var13] != null) {
									Block var14;
									if ((var14 = Block.BY_ID[var13]).getRenderLayer() != var7) {
										var8 = true;
									} else {
										var9 |= this.f_3953567.tessellateLiquid(var14, var12, var10, var11);
									}
								}
							}
						}
					}
				}

				BUFFER_BUILDER.end();
				GL11.glPopMatrix();
				GL11.glEndList();
				BUFFER_BUILDER.offset(0.0, 0.0, 0.0);
				if (var9) {
					this.blocks[var7] = false;
				}

				if (!var8) {
					break;
				}
			}

			this.hasSkyLight = WorldChunk.hasSkyLight;
		}
		ci.cancel();
	}
}
