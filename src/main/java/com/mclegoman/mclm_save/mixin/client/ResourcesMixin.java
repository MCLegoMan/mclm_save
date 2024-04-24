/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.data.ClientData;
import net.minecraft.client.C_5664496;
import net.minecraft.client.C_9029783;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_9029783.class)
public abstract class ResourcesMixin {
	@Shadow private C_5664496 f_6145320;
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void save$init(CallbackInfo ci) {
		ClientData.minecraft = this.f_6145320;
	}
}
