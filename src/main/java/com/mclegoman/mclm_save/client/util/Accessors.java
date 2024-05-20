/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.util;

import com.mclegoman.mclm_save.mixin.client.accessors.EntityAccessor;
import com.mclegoman.mclm_save.mixin.client.accessors.PlayerEntityAccessor;
import com.mclegoman.mclm_save.mixin.client.accessors.ScreenAccessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;

import java.awt.*;

public class Accessors {
	public static EntityAccessor getEntity(Entity entity) {
		return (EntityAccessor)entity;
	}
	public static PlayerEntityAccessor getPlayerEntity(PlayerEntity entity) {
		return (PlayerEntityAccessor)entity;
	}
	public static ScreenAccessor getScreen(Screen screen) {
		return (ScreenAccessor)screen;
	}
	public static class World {
		public static byte[] f_4249554;
		public static byte[] f_3132715;
	}
	public static class MinecraftClient {
		public static Canvas canvas;
		public static boolean shouldLoad;
		public static boolean shouldResize;
		public static net.minecraft.world.World world;
		public static void loadWorld(net.minecraft.world.World world) {
			shouldLoad = true;
			MinecraftClient.world = world;
		}
	}
}
