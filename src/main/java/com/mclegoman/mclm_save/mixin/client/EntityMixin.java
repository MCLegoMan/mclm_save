/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.common.entity.GetEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements GetEntity {
	@Shadow public float y;
	@Shadow public float x;
	@Shadow public float z;
	@Shadow public float width;
	@Shadow public float height;
	@Shadow public Box shape;
	@Shadow protected World world;
	@Inject(method = "setPosition", at = @At(value = "HEAD"), cancellable = true)
	private void save$checkCoords(float f, float g, float h, CallbackInfo ci) {
		if (save$getEntity() instanceof LivingEntity) {
			float newX = f;
			float newY = g;
			float newZ = h;
			if ((f <= this.world.f_3061106) && (h <= this.world.f_4184003)) {
				if (g <= this.world.f_8212213) {
					if (Block.BY_ID[this.world.m_5102244((int)newX, (int)newY, (int)newZ)] != null) newY = save$getY(newX, newZ);
				} else newY = save$getY(newX, newZ);
			} else {
				newX = save$getX();
				newZ = save$getZ();
				newY = save$getY(newX, newZ);
			}
			this.x = newX;
			this.y = newY;
			this.z = newZ;
			float var4 = this.width / 2.0F;
			float var5 = this.height / 2.0F;
			this.shape = new Box(newX - var4, newY - var5, newZ - var4, newX + var4, newY + var5, newZ + var4);
			ci.cancel();
		}
	}
	@Override
	public Entity save$getEntity() {
		return (Entity) ((GetEntity)this);
	}
	@Unique
	private float save$getX() {
		return Math.min(128, ((float) this.world.f_3061106 / 2));
	}
	@Unique
	private float save$getY(float newX, float newZ) {
		for (int worldY = 0; worldY < this.world.f_8212213; worldY++) {
			if (Block.BY_ID[this.world.m_5102244((int) newX, worldY, (int) newZ)] == null) {
				return worldY + 1;
			}
		}
		return this.world.f_8212213;
	}
	@Unique
	private float save$getZ() {
		return Math.min(128, ((float) this.world.f_4184003 / 2));
	}
}
