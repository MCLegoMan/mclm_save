/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.april_fools.AprilFoolsHelper;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void save$init(CallbackInfo ci) {
		if (AprilFoolsHelper.isAprilFools()) {
			AprilFoolsHelper.attack = new Random().nextInt(2147483647);
			AprilFoolsHelper.defence = new Random().nextInt(2147483647);
			AprilFoolsHelper.speed = new Random().nextInt(2147483647);
		}
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 0))
	private String save$player(String text) {
		return AprilFoolsHelper.isAprilFools() ? "Phantazap" : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 1))
	private String save$attack(String text) {
		return AprilFoolsHelper.isAprilFools() ? "ATK: " + AprilFoolsHelper.attack : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 2))
	private String save$defence(String text) {
		return AprilFoolsHelper.isAprilFools() ? "DEF: " + AprilFoolsHelper.defence : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 3))
	private String save$speed(String text) {
		return AprilFoolsHelper.isAprilFools() ? "SPD: " + AprilFoolsHelper.speed : text;
	}
}
