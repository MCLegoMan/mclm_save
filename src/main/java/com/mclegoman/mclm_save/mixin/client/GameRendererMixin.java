/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.level.SaveMinecraft;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Inject(method = "m_8576613", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V"))
	public void save$overlay(float f, CallbackInfo ci) {
		try {
			if (ClientData.minecraft.f_0426313 != null) {
				if (SaveMinecraft.currentWorld.getSaving()) {
					Window window = new Window(ClientData.minecraft.f_0545414, ClientData.minecraft.f_5990000);
					ClientData.minecraft.f_0426313.drawWithShadow("Saving World", window.m_2112110() - ClientData.minecraft.f_0426313.getWidth("Saving World") - 2, window.m_3634999() - 9 - 2, 16777215);
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "An error occurred whilst rendering overlay: " + error.getLocalizedMessage());
		}
	}
}
