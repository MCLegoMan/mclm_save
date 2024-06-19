/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.fixes.resize;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public abstract class GameGuiMixin {
	@Shadow private int f_0775750;
	@Shadow private int f_8519063;
	@Inject(method = "render", at = @At(value = "HEAD"))
	private void save$updateSize(CallbackInfo ci) {
		try {
			if (Accessors.MinecraftClient.shouldResize) {
				this.f_0775750 = ClientData.minecraft.f_0545414 * 240 / ClientData.minecraft.f_5990000;
				this.f_8519063 = ClientData.minecraft.f_5990000 * 240 / ClientData.minecraft.f_5990000;
				Accessors.MinecraftClient.shouldResize = false;
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Error updating gamegui size: " + error.getLocalizedMessage());
		}
	}
}