/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.config.SaveConfig;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	@Inject(method = "buttonClicked", at = @At(value = "RETURN"))
	private void save$useLoadFile(ButtonWidget button, CallbackInfo ci) {
		if (button.active && SaveConfig.instance.skipSaveLoadScreen.value()) {
			if (this.minecraft.f_2424468 != null) {
				if (button.id == 2) LevelFile.load(false);
				if (button.id == 3) LevelFile.load(true);
			}
		}
	}
}
