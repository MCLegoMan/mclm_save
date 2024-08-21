/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.world_render;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.C_2899740;
import net.minecraft.client.C_5664496;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.world.PendingChunkComparator;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.particle.ParticleManager;
import net.minecraft.unmapped.C_1022696;
import net.minecraft.unmapped.C_1640834;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.HitResult;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow private C_5664496 minecraft;

	@Shadow protected abstract void applyViewBobbing(float f);

	@Shadow protected abstract void applyHurtCam(float f);

	@Shadow public C_1022696 heldItemRenderer;

	@Shadow protected abstract void renderFog();

	@Shadow protected abstract Vec3d m_4942869(float f);

	@Shadow private float viewDistance;

	@Shadow private float fogBlue;

	@Shadow private float fogGreen;

	@Shadow private float fogRed;

	@Shadow private float newFogGrayScale;

	@Shadow private float oldFogGrayScale;

	@Shadow private Entity f_2112925;

	@Inject(method = "m_5195666", at = @At(value = "HEAD"), cancellable = true)
	public void save$m_5195666(float f, CallbackInfo ci) {
		try {
			InputPlayerEntity var15;
			float var16 = (var15 = this.minecraft.f_6058446).prevPitch + (var15.pitch - var15.prevPitch) * f;
			float var17 = var15.prevYaw + (var15.yaw - var15.prevYaw) * f;
			Vec3d var18 = this.m_4942869(f);
			float var19 = MathHelper.cos(-var17 * 0.017453292F - 3.1415927F);
			float var29 = MathHelper.sin(-var17 * 0.017453292F - 3.1415927F);
			float var30 = -MathHelper.cos(-var16 * 0.017453292F);
			float var31 = MathHelper.sin(-var16 * 0.017453292F);
			float var32 = var29 * var30;
			float var34 = var19 * var30;
			double var35 = (double)this.minecraft.f_1273243.getReach();
			Vec3d var37 = var18.add((double)var32 * var35, (double)var31 * var35, (double)var34 * var35);
			this.minecraft.f_3593768 = this.minecraft.f_5854988.rayTrace(var18, var37);
			double var38 = var35;
			var18 = this.m_4942869(f);
			if (this.minecraft.f_3593768 != null) {
				var38 = this.minecraft.f_3593768.offset.distanceTo(var18);
			}

			if (this.minecraft.f_1273243 instanceof C_1640834) {
				var35 = 32.0;
			} else {
				if (var38 > 3.0) {
					var38 = 3.0;
				}

				var35 = var38;
			}

			var37 = var18.add((double)var32 * var35, (double)var31 * var35, (double)var34 * var35);
			this.f_2112925 = null;
			List var40 = this.minecraft.f_5854988.getEntities(var15, var15.shape.grow((double)var32 * var35, (double)var31 * var35, (double)var34 * var35));
			double var41 = 0.0;

			int var14;
			double var48;
			HitResult var56;
			for(var14 = 0; var14 < var40.size(); ++var14) {
				Entity var53;
				if ((var53 = (Entity)var40.get(var14)).hasCollision() && (var56 = var53.shape.expand(0.10000000149011612, 0.10000000149011612, 0.10000000149011612).clip(var18, var37)) != null && ((var48 = var18.distanceTo(var56.offset)) < var41 || var41 == 0.0)) {
					this.f_2112925 = var53;
					var41 = var48;
				}
			}

			if (this.f_2112925 != null && !(this.minecraft.f_1273243 instanceof C_1640834)) {
				this.minecraft.f_3593768 = new HitResult(this.f_2112925);
			}

			InputPlayerEntity var2 = this.minecraft.f_6058446;
			World var3 = this.minecraft.f_5854988;
			WorldRenderer var4 = this.minecraft.f_4021716;
			ParticleManager var5 = this.minecraft.f_9322491;
			double var6 = var2.prevTickX + (var2.x - var2.prevTickX) * (double)f;
			double var8 = var2.prevTickY + (var2.y - var2.prevTickY) * (double)f;
			double var10 = var2.prevTickZ + (var2.z - var2.prevTickZ) * (double)f;

			for(int var12 = 0; var12 < 2; ++var12) {
				if (this.minecraft.f_9967940.f_5010661) {
					if (var12 == 0) {
						GL11.glColorMask(false, true, true, false);
					} else {
						GL11.glColorMask(true, false, false, false);
					}
				}

				GL11.glViewport(0, 0, this.minecraft.f_0545414, this.minecraft.f_5990000);
				World var54 = this.minecraft.f_5854988;
				InputPlayerEntity var57 = this.minecraft.f_6058446;
				var17 = 1.0F / (float)(4 - this.minecraft.f_9967940.f_7110074);
				var17 = 1.0F - (float)Math.pow((double)var17, 0.25);
				var19 = (float)(var18 = var54.getSkyColor(f)).x;
				var29 = (float)var18.y;
				var30 = (float)var18.z;
				Vec3d var66 = var54.getFogColor(f);
				this.fogRed = (float)var66.x;
				this.fogGreen = (float)var66.y;
				this.fogBlue = (float)var66.z;
				this.fogRed += (var19 - this.fogRed) * var17;
				this.fogGreen += (var29 - this.fogGreen) * var17;
				this.fogBlue += (var30 - this.fogBlue) * var17;
				Block var69;
				if ((var69 = Block.BY_ID[var54.m_9893076(MathHelper.floor(var57.x), MathHelper.floor(var57.y + 0.11999999731779099), MathHelper.floor(var57.z))]) != null && var69.material != Material.AIR) {
					Material var33;
					if ((var33 = var69.material) == Material.WATER) {
						this.fogRed = 0.02F;
						this.fogGreen = 0.02F;
						this.fogBlue = 0.2F;
					} else if (var33 == Material.LAVA) {
						this.fogRed = 0.6F;
						this.fogGreen = 0.1F;
						this.fogBlue = 0.0F;
					}
				}

				float var70 = this.newFogGrayScale + (this.oldFogGrayScale - this.newFogGrayScale) * f;
				this.fogRed *= var70;
				this.fogGreen *= var70;
				this.fogBlue *= var70;
				if (this.minecraft.f_9967940.f_5010661) {
					var34 = (this.fogRed * 30.0F + this.fogGreen * 59.0F + this.fogBlue * 11.0F) / 100.0F;
					float var72 = (this.fogRed * 30.0F + this.fogGreen * 70.0F) / 100.0F;
					float var36 = (this.fogRed * 30.0F + this.fogBlue * 70.0F) / 100.0F;
					this.fogRed = var34;
					this.fogGreen = var72;
					this.fogBlue = var36;
				}

				GL11.glClearColor(this.fogRed, this.fogGreen, this.fogBlue, 0.0F);
				GL11.glClear(16640);
				GL11.glEnable(2884);
				this.viewDistance = (float)(256 >> this.minecraft.f_9967940.f_7110074);
				GL11.glMatrixMode(5889);
				GL11.glLoadIdentity();
				if (this.minecraft.f_9967940.f_5010661) {
					GL11.glTranslatef((float)(-((var12 << 1) - 1)) * 0.07F, 0.0F, 0.0F);
				}

				InputPlayerEntity var64 = this.minecraft.f_6058446;
				var29 = 70.0F;
				if (var64.isSubmergedIn()) {
					var29 = 60.0F;
				}

				if (var64.health <= 0) {
					var30 = (float)var64.deathTime + f;
					var29 /= (1.0F - 500.0F / (var30 + 500.0F)) * 2.0F + 1.0F;
				}

				GLU.gluPerspective(var29, (float)this.minecraft.f_0545414 / (float)this.minecraft.f_5990000, 0.05F, this.viewDistance);
				GL11.glMatrixMode(5888);
				GL11.glLoadIdentity();
				if (this.minecraft.f_9967940.f_5010661) {
					GL11.glTranslatef((float)((var12 << 1) - 1) * 0.1F, 0.0F, 0.0F);
				}

				this.applyHurtCam(f);
				if (this.minecraft.f_9967940.f_7296792) {
					this.applyViewBobbing(f);
				}

				double var10001 = var64.x - var64.prevX;
				double var67 = (var64 = this.minecraft.f_6058446).prevX + var10001 * (double)f;
				double var68 = var64.prevY + (var64.y - var64.prevY) * (double)f;
				double var71 = var64.prevZ + (var64.z - var64.prevZ) * (double)f;
				int var52 = (int)f;
				if (!this.minecraft.f_9967940.f_3094045) {
					GL11.glTranslatef(0.0F, 0.0F, -0.1F);
				} else {
					var35 = 4.0;
					double var73 = (double)(-MathHelper.sin(var64.yaw / 180.0F * 3.1415927F) * MathHelper.cos(var64.pitch / 180.0F * 3.1415927F)) * 4.0;
					double var39 = (double)(MathHelper.cos(var64.yaw / 180.0F * 3.1415927F) * MathHelper.cos(var64.pitch / 180.0F * 3.1415927F)) * 4.0;
					var41 = (double)(-MathHelper.sin(var64.pitch / 180.0F * 3.1415927F)) * 4.0;

					for(var52 = 0; var52 < 8; ++var52) {
						float var55 = (float)(((var52 & 1) << 1) - 1);
						float var50 = (float)(((var52 >> 1 & 1) << 1) - 1);
						var16 = (float)(((var52 >> 2 & 1) << 1) - 1);
						var55 *= 0.1F;
						var50 *= 0.1F;
						var16 *= 0.1F;
						if ((var56 = minecraft.f_5854988.rayTrace(new Vec3d(var67 + (double)var55, var68 + (double)var50, var71 + (double)var16), new Vec3d(var67 - var73 + (double)var55 + (double)var16, var68 - var41 + (double)var50, var71 - var39 + (double)var16))) != null && (var48 = var56.offset.distanceTo(new Vec3d(var67, var68, var71))) < var35) {
							var35 = var48;
						}
					}

					GL11.glTranslatef(0.0F, 0.0F, (float)(-var35));
				}

				GL11.glRotatef(var64.prevPitch + (var64.pitch - var64.prevPitch) * var52, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(var64.prevYaw + (var64.yaw - var64.prevYaw) * var52 + 180.0F, 0.0F, 1.0F, 0.0F);
				Frustum.compute();
				this.renderFog();
				GL11.glEnable(2912);
				var4.renderSky(f);
				this.renderFog();
				FrustumCuller var51;
				(var51 = new FrustumCuller()).m_0871758(var6, var8, var10);
				this.minecraft.f_4021716.updateFrustums(var51);
				this.minecraft.f_4021716.m_8580944(var2);
				this.renderFog();
				GL11.glEnable(2912);
				GL11.glBindTexture(3553, this.minecraft.f_9413506.load("/terrain.png"));
				C_2899740.m_8989115();
				var4.render(var2, 0, (double)f);
				var14 = MathHelper.floor(var2.x);
				int var58 = MathHelper.floor(var2.y);
				int var60 = MathHelper.floor(var2.z);
				if (var3.m_9239141(var14, var58, var60)) {
					BlockRenderer var61 = new BlockRenderer(var3);

					for(int var63 = var14 - 1; var63 <= var14 + 1; ++var63) {
						for(int var65 = var58 - 1; var65 <= var58 + 1; ++var65) {
							for(int var20 = var60 - 1; var20 <= var60 + 1; ++var20) {
								int var21;
								if ((var21 = var3.m_9893076(var63, var65, var20)) > 0) {
									var61.m_7267636(Block.BY_ID[var21], var63, var65, var20);
								}
							}
						}
					}
				}

				C_2899740.m_3229588();
				GL11.glPushMatrix();
				var4.renderEntities(this.m_4942869(f), var51, f);
				var5.m_7371694(f);
				GL11.glPopMatrix();
				C_2899740.m_8989115();
				this.renderFog();
				var5.m_2631170(var2, f);
				if (this.minecraft.f_3593768 != null && var2.isSubmergedIn()) {
					GL11.glDisable(3008);
					var4.m_9853432(var2, this.minecraft.f_3593768, 0, var2.inventory.getMainHandStack(), f);
					var4.renderBlockOutline(var2, this.minecraft.f_3593768, 0, f);
					GL11.glEnable(3008);
				}

				GL11.glBlendFunc(770, 771);
				this.renderFog();
				GL11.glEnable(3042);
				GL11.glDisable(2884);
				GL11.glColorMask(false, false, false, false);
				GL11.glBindTexture(3553, this.minecraft.f_9413506.load("/terrain.png"));
				int var62 = var4.render(var2, 1, (double)f);
				GL11.glColorMask(true, true, true, true);
				if (this.minecraft.f_9967940.f_5010661) {
					if (var12 == 0) {
						GL11.glColorMask(false, true, true, false);
					} else {
						GL11.glColorMask(true, false, false, false);
					}
				}

				if (var62 > 0) {
					GL11.glBindTexture(3553, this.minecraft.f_9413506.load("/terrain.png"));
					var4.m_0907931(1, (double)f);
				}

				GL11.glDepthMask(true);
				GL11.glEnable(2884);
				GL11.glDisable(3042);
				if (this.minecraft.f_3593768 != null && !var2.isSubmergedIn()) {
					GL11.glDisable(3008);
					var4.m_9853432(var2, this.minecraft.f_3593768, 0, var2.inventory.getMainHandStack(), f);
					var4.renderBlockOutline(var2, this.minecraft.f_3593768, 0, f);
					GL11.glEnable(3008);
				}

				GL11.glDisable(2912);
				GL11.glClear(256);
				GL11.glLoadIdentity();
				if (this.minecraft.f_9967940.f_5010661) {
					GL11.glTranslatef((float)((var12 << 1) - 1) * 0.1F, 0.0F, 0.0F);
				}

				GL11.glPushMatrix();
				this.applyHurtCam(f);
				if (this.minecraft.f_9967940.f_7296792) {
					this.applyViewBobbing(f);
				}

				if (!this.minecraft.f_9967940.f_3094045) {
					this.heldItemRenderer.m_8801525(f);
				}

				GL11.glPopMatrix();
				if (!this.minecraft.f_9967940.f_3094045) {
					this.heldItemRenderer.m_6811825(f);
					this.applyHurtCam(f);
				}

				if (this.minecraft.f_9967940.f_7296792) {
					this.applyViewBobbing(f);
				}

				if (!this.minecraft.f_9967940.f_5010661) {
					return;
				}
			}

			GL11.glColorMask(true, true, true, false);
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst sorting GameRenderer/m_5195666: " + error.getLocalizedMessage());
		}
		ci.cancel();
	}
}
