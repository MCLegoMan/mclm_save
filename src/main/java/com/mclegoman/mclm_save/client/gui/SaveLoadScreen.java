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
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.mclm_save.config.Theme;
import com.mclegoman.releasetypeutils.common.version.Helper;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

public class SaveLoadScreen extends Thread {
	private final boolean isLoad;
	public SaveLoadScreen(boolean isLoad) {
		this.isLoad = isLoad;
	}
	public void run() {
		File openInPath = new File(SaveConfig.instance.dialogDir.value());
		if (!openInPath.exists() || !openInPath.isDirectory()) openInPath = new File(SaveConfig.instance.dialogDir.getDefaultValue());
		JFileChooser fileChooser = new JFileChooser(openInPath);
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
			fileChooser.updateUI();
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, "Error setting Save/Load dialog theme: " + error.getLocalizedMessage());
		}
		FileNameExtensionFilter[] filters = new FileNameExtensionFilter[3];
		if (isLoad) {
			filters[0] = new FileNameExtensionFilter("Minecraft level (.mclevel, .mine, .dat)", "mclevel", "mine", "dat");
			filters[1] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
			filters[2] = new FileNameExtensionFilter("Classic Minecraft level (.mine, .dat)", "mine", "dat");
		} else {
			filters[0] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
		}
		for (FileNameExtensionFilter filter : filters) fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filters[0]);
			ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", isLoad ? "Select a world": "Select directory", InfoScreen.Type.DIRT, false));
		if ((isLoad ? fileChooser.showOpenDialog(Accessors.MinecraftClient.canvas) : fileChooser.showSaveDialog(Accessors.MinecraftClient.canvas)) == 0) {
			ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Please wait...", InfoScreen.Type.DIRT, false));
			LevelFile.file = fileChooser.getSelectedFile();
			LevelFile.isLoad = isLoad;
			LevelFile.shouldProcess = true;
		} else {
			ClientData.minecraft.m_6408915(null);
		}
		if (LevelFile.file != null) SaveConfig.instance.dialogDir.setValue(String.valueOf(LevelFile.file.toPath().getParent().toFile()));
		interrupt();
	}
}
