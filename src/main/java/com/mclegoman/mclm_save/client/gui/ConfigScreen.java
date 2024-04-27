/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.mclm_save.config.Theme;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;

public class ConfigScreen extends Screen {
	private final Screen parent;
	public ConfigScreen(Screen screen) {
		this.parent = screen;
	}
	public final void init() {
		this.buttons.add(new ButtonWidget(0, this.width / 2 - 150, this.height / 6, 300, 20, "Skip Save/Load Screen: " + SaveConfig.instance.skipSaveLoadScreen.value()));
		this.buttons.add(new ButtonWidget(1, this.width / 2 - 150, this.height / 6 + 24, 300, 20, "Force April Fools: " + SaveConfig.instance.forceAprilFools.value()));
		ButtonWidget convertClassicInv = new ButtonWidget(2, this.width / 2 - 150, this.height / 6 + 48, 300, 20, "Convert Classic Inventory: " + SaveConfig.instance.convertClassicInv.value());
		convertClassicInv.active = false;
		this.buttons.add(convertClassicInv);
		this.buttons.add(new ButtonWidget(3, this.width / 2 - 150, this.height / 6 + 72, 300, 20, "Dialog Theme: " + SaveConfig.instance.dialogTheme.value().getName()));
		this.buttons.add(new ButtonWidget(4, this.width / 2 - 100, this.height / 6 + 168, 200, 20, "Done"));
	}

	protected void buttonClicked(net.minecraft.client.gui.widget.ButtonWidget button) {
		if (button.active) {
			if (button.id == 0) {
				SaveConfig.instance.skipSaveLoadScreen.setValue(!SaveConfig.instance.skipSaveLoadScreen.value());
				button.message = "Skip Save/Load Screen: " + SaveConfig.instance.skipSaveLoadScreen.value();
			}
			if (button.id == 1) {
				SaveConfig.instance.forceAprilFools.setValue(!SaveConfig.instance.forceAprilFools.value());
				button.message = "Force April Fools: " + SaveConfig.instance.forceAprilFools.value();
			}
			if (button.id == 2) {
				SaveConfig.instance.convertClassicInv.setValue(!SaveConfig.instance.convertClassicInv.value());
				button.message = "Convert Classic Inventory: " + SaveConfig.instance.convertClassicInv.value();
			}
			if (button.id == 3) {
				Theme theme = SaveConfig.instance.dialogTheme.value();
				if (theme.equals(Theme.system)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						SaveConfig.instance.dialogTheme.setValue(Theme.material);
					} else {
						SaveConfig.instance.dialogTheme.setValue(Theme.light);
					}
				} else if (theme.equals(Theme.light)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						SaveConfig.instance.dialogTheme.setValue(Theme.system);
					} else {
						SaveConfig.instance.dialogTheme.setValue(Theme.dark);
					}
				} else if (theme.equals(Theme.dark)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						SaveConfig.instance.dialogTheme.setValue(Theme.light);
					} else {
						SaveConfig.instance.dialogTheme.setValue(Theme.metal);
					}
				} else if (theme.equals(Theme.metal)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						SaveConfig.instance.dialogTheme.setValue(Theme.dark);
					} else {
						SaveConfig.instance.dialogTheme.setValue(Theme.material);
					}
				} else if (theme.equals(Theme.material)) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
						SaveConfig.instance.dialogTheme.setValue(Theme.metal);
					} else {
						SaveConfig.instance.dialogTheme.setValue(Theme.system);
					}
				}
				button.message = "Dialog Theme: " + SaveConfig.instance.dialogTheme.value().getName();
			}
			if (button.id == 4) {
				SaveConfig.instance.save();
				ClientData.minecraft.m_6408915(this.parent);
			}
		}
	}
	public final void render(int i, int j) {
		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		drawCenteredString(this.textRenderer, "Save Config", this.width / 2, 20, 16777215);
		super.render(i, j);
	}
	public void keyPressed(char chr, int key) {
		if (key == 1) {
			SaveConfig.instance.save();
			ClientData.minecraft.m_6408915(this.parent);
		}
	}
}
