/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.SaveConfig;
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
	@Shadow private float width;
	@Shadow public float height;
	@Shadow public Box shape;
	@Shadow public World world;
	@Inject(method = "setPosition", at = @At(value = "HEAD"), cancellable = true)
	private void save$checkCoords(float x, float g, float z, CallbackInfo ci) {
		try {
			if (SaveConfig.instance.blockPosFix.value()) {
				float newY = save$getY(x, g, z);
				this.x = x;
				this.y = newY;
				this.z = z;
				float halfWidth = this.width / 2.0F;
				float halfHeight = this.height / 2.0F;
				this.shape = new Box(x - halfWidth, newY - halfHeight, z - halfWidth, x + halfWidth, newY + halfHeight, z + halfWidth);
				ci.cancel();
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "There was an error changing entity position: " + error.getLocalizedMessage());
		}
	}
	@Unique
	private float save$getY(float x, float y, float z) {
		float newY = y;
		if (newY <= this.world.f_8212213) {
			for (float worldY = newY; worldY < this.world.f_8212213; worldY++) {
				int blockId = this.world.m_5102244((int) x, (int) worldY, (int) z);
				if (blockId == 0) {
					newY = worldY - 0.5F;
					break;
				}
			}
		}
		return newY;
	}
}
