/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.level.LevelFile;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
	@Inject(method = "buttonClicked", at = @At(value = "RETURN"))
	private void save$useLoadFile(ButtonWidget button, CallbackInfo ci) {
		if (button.active) {
			if (button.id == 2) LevelFile.load(true);
		}
	}
	@Inject(method = "render", at = @At(value = "RETURN"))
	private void save$render(int j, int par2, CallbackInfo ci) {
		if (ClientData.minecraft.f_6058446.health > 0) {
			ClientData.minecraft.f_6058446.deathTime = 0;
			ClientData.minecraft.m_6408915(null);
		}
	}
}
