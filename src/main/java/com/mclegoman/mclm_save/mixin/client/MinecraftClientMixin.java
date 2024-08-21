/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.Save;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import net.minecraft.client.C_5664496;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Optional;

@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Shadow public Canvas f_0769488;
	@Shadow public abstract void m_9890357(World world);
	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick(CallbackInfo ci) {
		// Updates Canvas Accessor and checks if the world needs to load.
		Accessors.MinecraftClient.canvas = this.f_0769488;
		if (Accessors.MinecraftClient.shouldLoad && Accessors.MinecraftClient.world != null) {
			Accessors.MinecraftClient.shouldLoad = false;
			this.m_9890357(Accessors.MinecraftClient.world);
		}
		// Runs our Save::onTick function.
		Optional<ModContainer> modContainer = QuiltLoader.getModContainer(Data.version.getID());
		modContainer.ifPresent(Save::onTick);
	}
	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;<init>(Lnet/minecraft/client/C_5664496;)V"))
	private void save$run(CallbackInfo ci) {
		// Runs our Save::onInitialize function.
		Optional<ModContainer> modContainer = QuiltLoader.getModContainer(Data.version.getID());
		modContainer.ifPresent(Save::onInitialize);
	}
}