package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.SaveEntity;
import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InputPlayerEntity.class)
public abstract class InputPlayerEntityMixin extends PlayerEntity implements SaveEntity {
	public InputPlayerEntityMixin(World world) {
		super(world);
	}
	public void mclm_save$writeCustomNbt(NbtCompound nbtCompound) {
		nbtCompound.putShort("Health", (short)this.health);
		nbtCompound.putShort("HurtTime", (short)this.hurtTime);
		nbtCompound.putShort("DeathTime", (short)this.deathTime);
		nbtCompound.putShort("AttackTime", (short)this.attackTime);
		nbtCompound.putInt("Score", this.playerScore);
	}
	public String mclm_save$entityId() {
		return null;
	}
}
