/*
    mclm_debug
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_debug
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes;

import com.mclegoman.mclm_save.common.data.Data;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftApplet.class)
public abstract class MinecraftAppletMixin {
	@Inject(method = "stop", at = @At(value = "HEAD"))
	private void debug$stop(CallbackInfo ci) {
		Data.exit(0);
	}
}