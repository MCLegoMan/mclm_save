/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.client.util.Accessors;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.unmapped.C_0388903;
import net.minecraft.unmapped.C_5540851;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5540851.class)
public abstract class LoadScreenMixin extends Screen {
	@Shadow
	protected abstract void m_1871619(String[] strings);
	@Shadow
	private boolean f_6250022;
	@Inject(method = "run", at = @At(value = "HEAD"), cancellable = true)
	private void save$overrideLevelList(CallbackInfo ci) {
		this.m_1871619(new String[]{"-","-","-","-","-"});
		this.f_6250022 = true;
		ci.cancel();
	}
	@Inject(method = "init", at = @At(value = "RETURN"))
	private void save$enableLoadFile(CallbackInfo ci) {
		for (int i = 0; i < 4; i++) ((ButtonWidget)this.buttons.get(i)).active = false;
		((ButtonWidget)this.buttons.get(5)).visible = true;
	}
	@Inject(method = "buttonClicked", at = @At(value = "RETURN"))
	private void save$useLoadFile(ButtonWidget button, CallbackInfo ci) {
		if (button.active && button.id == 5) LevelFile.load(!(Accessors.MinecraftClient.screen instanceof C_0388903));
	}
}
