/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.util.Accessors;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow
	private byte[] f_4249554;
	@Shadow
	private byte[] f_3132715;
	@Inject(method = "m_6536802", at = @At("TAIL"))
	private void save$tick(CallbackInfo ci) {
		Accessors.World.f_4249554 = this.f_4249554;
		Accessors.World.f_3132715 = this.f_3132715;
	}
}
