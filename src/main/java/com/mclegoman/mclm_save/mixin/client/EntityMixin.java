/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
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
			float newY = save$getY(f, g, h);
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
	private float save$getY(float f, float g, float h) {
		float newY = g;
		if (g > this.world.f_8212213) {
			if (world.f_2923303 > this.world.f_8212213) {
				newY = (float) world.f_8212213;
			}
			else {
				newY = world.f_2923303;
			}
		}
		for (float worldY = newY; worldY < this.world.f_8212213; worldY++) {
			int blockId = this.world.m_5102244((int) f, (int) worldY, (int) h);
			if (blockId == 0) {
				newY = worldY - 0.5F;
				break;
			}
		}
		int blockId = this.world.m_5102244((int) f, (int)newY, (int) h);
		if (blockId != 0) newY = this.world.f_8212213;
		return newY;
	}
}
