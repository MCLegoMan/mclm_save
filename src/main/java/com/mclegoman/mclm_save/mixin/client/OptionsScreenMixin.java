/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.gui.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
	@Shadow private Screen parent;
	@Inject(method = "init", at = @At(value = "TAIL"))
	private void save$init(CallbackInfo ci) {
		this.buttons.add(new ButtonWidget(1000, this.width / 2 - 100, this.height / 6 + 110, "Save Config"));
	}
	@Inject(method = "buttonClicked", at = @At(value = "TAIL"))
	private void save$buttonClicked(ButtonWidget button, CallbackInfo ci) {
		if (button.active) {
			if (button.id == 1000) {
				ClientData.minecraft.m_6408915(new ConfigScreen(new OptionsScreen(this.parent, ClientData.minecraft.f_9967940)));
			}
		}
	}
}
