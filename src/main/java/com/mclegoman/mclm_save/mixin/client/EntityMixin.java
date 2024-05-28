/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow public float y;
	@Shadow public float x;
	@Shadow public float z;
	@Shadow public float width;
	@Shadow public float height;
	@Shadow public Box shape;
	@Shadow protected World world;
	@Inject(method = "setPosition", at = @At(value = "HEAD"), cancellable = true)
	private void save$checkCoords(float f, float g, float h, CallbackInfo ci) {
		try {
			float newY = g;
			if ((int)g > this.world.f_8212213) {
				if (world.f_2923303 > this.world.f_8212213) {
					newY = (float) world.f_8212213;
				}
				else {
					newY = world.f_2923303;
				}
			}
			for (int worldY = (int) newY; worldY < this.world.f_8212213; worldY++) {
				int blockId = this.world.m_5102244((int) f, worldY, (int) h);
				if (blockId == 0) {
					// We add one so the player isn't half in a block.
					newY = worldY + 1;
					break;
				}
			}
			this.x = f;
			this.y = newY;
			this.z = h;
			float var4 = this.width / 2.0F;
			float var5 = this.height / 2.0F;
			this.shape = new Box(f - var4, newY - var5, h - var4, f + var4, newY + var5, h + var4);
			ci.cancel();
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "There was an error changing entity position: " + error.getLocalizedMessage());
		}
	}
	@Unique
	private float save$getY(float newX, float newY, float newZ) {
		if (newY <= this.world.f_8212213 && newY >= 0) {
			if (Block.BY_ID[this.world.m_5102244((int) newX, (int)newY, (int) newZ)] == null) return newY;
			else {
				for (int worldY = (int) newY; worldY < this.world.f_8212213; worldY++) {
					if (Block.BY_ID[this.world.m_5102244((int) newX, worldY, (int) newZ)] == null) {
						return worldY + 1;
					}
				}
			}
		} else if (this.world.f_2923303 <= this.world.f_8212213) {
			if (Block.BY_ID[this.world.m_5102244((int) newX, this.world.f_2923303, (int) newZ)] == null) return this.world.f_2923303;
			else {
				for (int worldY = this.world.f_2923303; worldY < this.world.f_8212213; worldY++) {
					if (Block.BY_ID[this.world.m_5102244((int) newX, worldY, (int) newZ)] == null) {
						return worldY + 1;
					}
				}
			}
		} else {
			for (int worldY = 0; worldY < this.world.f_8212213; worldY++) {
				if (Block.BY_ID[this.world.m_5102244((int) newX, worldY, (int) newZ)] == null) {
					return worldY + 1;
				}
			}
		}
		return this.world.f_8212213;
	}
}
