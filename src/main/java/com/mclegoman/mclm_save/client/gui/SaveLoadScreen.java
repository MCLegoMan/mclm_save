/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.client.util.FileNameFilter;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.Filter;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.mclm_save.config.Theme;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

public class SaveLoadScreen extends Thread {
	private final boolean isLoad;
	public SaveLoadScreen(boolean isLoad) {
		this.isLoad = isLoad;
	}
	public void run() {
		if (Accessors.MinecraftClient.canvas != null) {
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
				fileChooser.updateUI();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.WARN, "Error setting Save/Load dialog theme: " + error.getLocalizedMessage());
			}
			FileFilter[] filters = new FileFilter[4];
			if (isLoad) {
				filters[0] = new FileNameExtensionFilter("Minecraft level (.mclevel, .mine, .dat)", "mclevel", "mine", "dat");
				filters[1] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
				filters[2] = new FileNameExtensionFilter("Classic Minecraft level (.mine, .dat)", "mine", "dat");
				filters[3] = new FileNameFilter("Infdev Minecraft level (level.dat)", "level.dat");
			} else {
				filters[0] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
			}
			for (FileFilter filter : filters) fileChooser.addChoosableFileFilter(filter);
			if (isLoad) {
				if (SaveConfig.instance.loadDialogFilter.value() == Filter.minecraft) {
					fileChooser.setFileFilter(filters[0]);
				} else if (SaveConfig.instance.loadDialogFilter.value() == Filter.indev) {
					fileChooser.setFileFilter(filters[1]);
				} else if (SaveConfig.instance.loadDialogFilter.value() == Filter.classic) {
					fileChooser.setFileFilter(filters[2]);
				}
			} else {
				if (SaveConfig.instance.saveDialogFilter.value() == Filter.indev) {
					fileChooser.setFileFilter(filters[0]);
				}
			}
			ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", isLoad ? "Select a world": "Select directory", InfoScreen.Type.DIRT, false));
			if ((isLoad ? fileChooser.showOpenDialog(Accessors.MinecraftClient.canvas) : fileChooser.showSaveDialog(Accessors.MinecraftClient.canvas)) == 0) {
				ClientData.minecraft.m_6408915(new InfoScreen(isLoad ? "Loading World" : "Saving World", "Please wait...", InfoScreen.Type.DIRT, false));
				//LevelFile.file = fileChooser.getSelectedFile();
				//LevelFile.isLoad = isLoad;
				//LevelFile.shouldProcess = true;
			} else {
				ClientData.minecraft.m_6408915(null);
			}
//			if (LevelFile.file != null) {
//				SaveConfig.instance.dialogDir.setValue(String.valueOf(LevelFile.file.toPath().getParent().toFile()));
//				if (isLoad) {
//					if (fileChooser.getFileFilter() == filters[0]) {
//						SaveConfig.instance.loadDialogFilter.setValue(Filter.minecraft);
//					} else if (fileChooser.getFileFilter() == filters[1]) {
//						SaveConfig.instance.loadDialogFilter.setValue(Filter.indev);
//					} else if (fileChooser.getFileFilter() == filters[2]) {
//						SaveConfig.instance.loadDialogFilter.setValue(Filter.classic);
//					} else {
//						SaveConfig.instance.loadDialogFilter.setValue(Filter.all);
//					}
//				} else {
//					if (fileChooser.getFileFilter() == filters[0]) {
//						SaveConfig.instance.saveDialogFilter.setValue(Filter.indev);
//					} else {
//						SaveConfig.instance.saveDialogFilter.setValue(Filter.all);
//					}
//				}
//			}
			interrupt();
		} else {
			ClientData.minecraft.m_6408915(new InfoScreen("Saving/Loading World", "Canvas doesn't seem to exist.", InfoScreen.Type.ERROR, true));
		}
	}
}
