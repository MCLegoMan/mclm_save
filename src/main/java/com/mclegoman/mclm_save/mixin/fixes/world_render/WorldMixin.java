/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.world_render;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow public abstract int m_9893076(int i, int j, int k);
	@Inject(method = "m_5294276", at = @At(value = "HEAD"), cancellable = true)
	public void save$getMaterial(int i, int j, int k, CallbackInfoReturnable<Material> cir) {
		cir.setReturnValue((i = this.m_9893076(i, j, k)) == 0 ? Material.AIR : (i < Block.BY_ID.length && Block.BY_ID[i] != null ? Block.BY_ID[i].material : Material.AIR));
	}
}
