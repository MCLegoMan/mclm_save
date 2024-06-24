/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.resize;

import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;

@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Shadow private Canvas f_0769488;
	@Shadow public int f_0545414;
	@Shadow public int f_5990000;
	@Shadow private Screen f_0723335;
	@Shadow public abstract void m_6408915(Screen screen);
	@Shadow public abstract void m_5690108();

	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick(CallbackInfo ci) {
		try {
			if (LevelFile.dialog == null || !LevelFile.dialog.isAlive()) {
				boolean refreshScreen = false;
				if (this.f_0545414 != this.f_0769488.getParent().getWidth()) {
					this.f_0545414 = this.f_0769488.getParent().getWidth();
					refreshScreen = true;
				}
				if (this.f_5990000 != this.f_0769488.getParent().getHeight()) {
					this.f_5990000 = this.f_0769488.getParent().getHeight();
					refreshScreen = true;
				}
				if (refreshScreen) {
					if (this.f_0723335 != null) {
						Screen screen = this.f_0723335;
						Accessors.getScreen(screen).setButtons(new ArrayList<>());
						this.m_5690108();
						this.m_6408915(screen);
					}
					Accessors.MinecraftClient.shouldResize = true;
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Error setting canvas size: " + error.getLocalizedMessage());
		}
	}
}