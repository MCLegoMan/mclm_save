/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.util;

import com.mclegoman.mclm_save.client.data.ClientData;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;

public class Debug {
	public static String getCoords(InputPlayerEntity player) {
		return "x:" + player.x + " y:" + player.y + " z:" + player.z;
	}
	private static boolean showFpsLock;
	public static boolean isDebug;
	public static Screen currentScreen;
	public static void setDebug() {
		if (currentScreen == null) {
			boolean isDebugDown = Keyboard.isKeyDown(Keyboard.KEY_F3);
			if (!showFpsLock && isDebugDown) {
				showFpsLock = true;
				ClientData.minecraft.f_9967940.f_5294533 = !ClientData.minecraft.f_9967940.f_5294533;
			}
			if (showFpsLock && !isDebugDown) showFpsLock = false;
		}
		isDebug = ClientData.minecraft.f_9967940.f_5294533;
	}
}
