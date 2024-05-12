/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.client;

import com.mclegoman.mclm_save.client.april_fools.AprilFools;
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
		if (AprilFools.isAprilFools()) {
			AprilFools.name = new Random().nextInt(AprilFools.playerNames.size());
			AprilFools.nameChance = new Random().nextInt(1200);
			AprilFools.attack = new Random().nextInt(2147483647);
			AprilFools.defence = new Random().nextInt(2147483647);
			AprilFools.speed = new Random().nextInt(2147483647);
		}
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 0))
	private String save$player(String text) {
		return AprilFools.isAprilFools() ? AprilFools.getPlayerName() : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 1))
	private String save$attack(String text) {
		return AprilFools.isAprilFools() ? "ATK: " + AprilFools.attack : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 2))
	private String save$defence(String text) {
		return AprilFools.isAprilFools() ? "DEF: " + AprilFools.defence : text;
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)V", ordinal = 3))
	private String save$speed(String text) {
		return AprilFools.isAprilFools() ? "SPD: " + AprilFools.speed : text;
	}
}
