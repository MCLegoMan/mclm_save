/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.level.LevelFile;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.mclm_save.config.Theme;
import mdlaf.MaterialLookAndFeel;
import org.quiltmc.loader.api.QuiltLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

public class OpenLoadScreen extends Thread {
	private final boolean isLoad;
	public OpenLoadScreen(boolean isLoad) {
		this.isLoad = isLoad;
	}
	public void run() {
		JFileChooser var1 = new JFileChooser();
		try {
			Theme theme = SaveConfig.instance.dialogTheme.value();
			if (theme.equals(Theme.system)) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (theme.equals(Theme.light)) {
				UIManager.setLookAndFeel(new FlatIntelliJLaf());
			}
			else if (theme.equals(Theme.dark)) {
				UIManager.setLookAndFeel(new FlatDarculaLaf());
			}
			else if (theme.equals(Theme.metal)) {
				UIManager.setLookAndFeel(new MetalLookAndFeel());
			}
			else if (theme.equals(Theme.material)) {
				UIManager.setLookAndFeel(new MaterialLookAndFeel());
			}
		} catch (Exception ignored) {
		}
		var1.updateUI();
		File openInPath = new File(SaveConfig.instance.dialogDir.value());
		if (!openInPath.exists()) openInPath = QuiltLoader.getGameDir().toFile();
		var1.setCurrentDirectory(openInPath);
		FileNameExtensionFilter var2 = isLoad ? new FileNameExtensionFilter("Minecraft levels (.mclevel, .mine, .dat)", "mclevel", "mine", "dat") : new FileNameExtensionFilter("Minecraft level (.mclevel)", "mclevel");
		var1.setFileFilter(var2);
		var1.setLocation(var1.getLocation().x, var1.getLocation().y);
		ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", isLoad ? "Select a world": "Select directory", InfoScreen.Type.DIRT, false));
		if ((isLoad ? var1.showOpenDialog(Accessors.MinecraftClient.canvas) : var1.showSaveDialog(Accessors.MinecraftClient.canvas)) == 0) {
			LevelFile.file = var1.getSelectedFile();
			LevelFile.isLoad = isLoad;
			LevelFile.shouldLoad = true;
		} else {
			ClientData.minecraft.m_6408915(null);
		}
		if (LevelFile.file != null) SaveConfig.instance.dialogDir.setValue(String.valueOf(LevelFile.file.toPath().getParent().toFile()));
		interrupt();
	}
}
