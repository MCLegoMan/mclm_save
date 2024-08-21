/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.unmapped.C_5540851;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5540851.class)
public abstract class LoadScreenMixin extends Screen {
	@Inject(method = "run", at = @At(value = "HEAD"), cancellable = true)
	private void save$init(CallbackInfo ci) {
		this.minecraft.f_3533455 = "betacraft.uk";
	}
}
