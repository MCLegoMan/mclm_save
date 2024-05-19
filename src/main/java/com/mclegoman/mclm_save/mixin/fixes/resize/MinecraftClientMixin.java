/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.resize;

import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.C_5664496;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Shadow private Canvas f_0769488;
	@Shadow public int f_0545414;
	@Shadow public int f_5990000;
	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick(CallbackInfo ci) {
		// Resizes the game to fit in the game window.
		// TODO: Fix Overlays not being in the right place after resizing.
		// TODO: Fix Screens not being updated after resizing, and fix screen button positions.
		try {
			if (LevelFile.dialog == null || !LevelFile.dialog.isAlive()) {
				this.f_0545414 = this.f_0769488.getParent().getWidth();
				this.f_5990000 = this.f_0769488.getParent().getHeight();
				this.f_0769488.setSize(this.f_0545414, this.f_5990000);
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Error setting canvas size: " + error.getLocalizedMessage());
		}
	}
}