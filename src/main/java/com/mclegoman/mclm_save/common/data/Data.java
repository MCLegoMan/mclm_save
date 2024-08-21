/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.common.data;

import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.version.Helper;
import com.mclegoman.releasetypeutils.common.version.Version;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;

public class Data {
	public static String mcVersion = "inf-20100325-2";
	public static Version version = new Version("Save", "mclm_save", 1, 0, 2, Helper.ReleaseType.RELEASE, 1);
	public static void exit(int status) {
		version.sendToLog(Helper.LogType.INFO, "Halting with status code: " + status + "!");
		if (SaveConfig.instance.saveWorldOnExit.value()) {
			try {
				//new World().save(ClientData.minecraft.f_5854988, new File(QuiltLoader.getGameDir() + (QuiltLoader.getGameDir().endsWith("/") ? "" : "/") + "level.mclevel"));
			} catch (Exception error) {
				Data.version.sendToLog(Helper.LogType.WARN, "Failed to save world on exit: " + error);
			}
		}
		if (Mouse.isCreated()) Mouse.destroy();
		if (Keyboard.isCreated()) Keyboard.destroy();
		if (AL.isCreated()) AL.destroy();
		Thread.currentThread().interrupt();
		Runtime.getRuntime().halt(status);
	}
}
