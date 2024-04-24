/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.unmapped.C_0388903;
import net.minecraft.unmapped.C_5540851;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_0388903.class)
public abstract class SaveScreenMixin extends C_5540851 {
	public SaveScreenMixin(Screen screen) {
		super(screen);
	}
	@Inject(method = "m_1871619", at = @At(value = "HEAD"))
	private void save$removeSaveButtons(CallbackInfo ci) {
		for(int var2 = 0; var2 < 5; ++var2) {
			((ButtonWidget)this.buttons.get(var2)).message = "-";
			((ButtonWidget)this.buttons.get(var2)).active = false;
		}
	}
}
