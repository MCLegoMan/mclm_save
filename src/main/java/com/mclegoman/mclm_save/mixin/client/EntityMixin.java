package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.SaveEntity;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtFloat;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements SaveEntity {
	@Shadow public boolean removed;
	@Shadow public float yaw;
	@Shadow public float pitch;
	@Shadow private float fallDistance;
	@Shadow public int onFireTimer;
	@Shadow public int f_1297340;
	@Shadow public double velocityX;
	@Shadow public double velocityY;
	@Shadow public double velocityZ;
	@Shadow public double x;
	@Shadow public double y;
	@Shadow public double z;
	public void mclm_save$writeEntityNbt(NbtCompound nbtCompound) {
		String var2 = this.mclm_save$entityId();
		if (!this.removed && var2 != null) {
			nbtCompound.putString("id", var2);
			nbtCompound.put("Pos", mclm_save$toNbtList(this.x, this.y, this.z));
			nbtCompound.put("Motion", mclm_save$toNbtList(this.velocityX, this.velocityY, this.velocityZ));
			float[] var7 = new float[]{this.yaw, this.pitch};
			NbtList var3 = new NbtList();
			for (float var6 : var7) var3.add(new NbtFloat(var6));
			nbtCompound.put("Rotation", var3);
			nbtCompound.m_2391638("FallDistance", this.fallDistance);
			nbtCompound.putShort("Fire", (short)this.onFireTimer);
			nbtCompound.putShort("Air", (short)this.f_1297340);
			this.mclm_save$writeCustomNbt(nbtCompound);
		}
	}
	public abstract void mclm_save$writeCustomNbt(NbtCompound nbtCompound);
	public abstract void mclm_save$readCustomNbt(NbtCompound nbtCompound);
	public abstract String mclm_save$entityId();
}
