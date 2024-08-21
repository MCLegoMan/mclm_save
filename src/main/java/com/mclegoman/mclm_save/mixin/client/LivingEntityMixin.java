package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.SaveEntity;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SaveEntity {
	@Shadow public int health;
	@Shadow public int hurtTime;
	@Shadow public int deathTime;
	@Shadow public int attackTime;
	public void mclm_save$writeCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putShort("Health", (short)this.health);
		nbtCompound.putShort("HurtTime", (short)this.hurtTime);
		nbtCompound.putShort("DeathTime", (short)this.deathTime);
		nbtCompound.putShort("AttackTime", (short)this.attackTime);
	}
	public String mclm_save$entityId() {
		return "Mob";
	}
}
