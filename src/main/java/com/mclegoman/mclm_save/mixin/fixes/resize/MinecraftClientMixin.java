/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.resize;

//import com.mclegoman.mclm_save.client.level.LevelFile;
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

import java.util.ArrayList;

@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Shadow public Screen f_0723335;
	@Shadow public abstract void m_6408915(Screen screen);

	@Inject(method = "m_3347295", at = @At(value = "TAIL"))
	private void save$resize(int x, int y, CallbackInfo ci) {
		try {
//			if (LevelFile.dialog == null || !LevelFile.dialog.isAlive()) {
//				if (this.f_0723335 != null) {
//					Screen screen = this.f_0723335;
//					Accessors.getScreen(screen).setButtons(new ArrayList());
//					this.m_6408915(null);
//					this.m_6408915(screen);
//				}
//			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Error reloading screen after resize: " + error.getLocalizedMessage());
		}
	}
}