/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.mclm_save.config.Themes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ConfigScreen extends Screen {
	private final Screen parent;
	public ConfigScreen(Screen screen) {
		this.parent = screen;
	}
	public final void init() {
		this.buttons.add(new ButtonWidget(0, this.width / 2 - 100, this.height / 6, "Force April Fools: " + SaveConfig.instance.forceAprilFools.value()));
		ButtonWidget convertClassicInv = new ButtonWidget(1, this.width / 2 - 100, this.height / 6 + 24, "Convert Classic Inventory: " + SaveConfig.instance.convertClassicInv.value());
		convertClassicInv.active = false;
		this.buttons.add(convertClassicInv);
		this.buttons.add(new ButtonWidget(2, this.width / 2 - 100, this.height / 6 + 48, "Dialog Theme: " + SaveConfig.instance.dialogTheme.value().getName()));
		this.buttons.add(new ButtonWidget(3, this.width / 2 - 100, this.height / 6 + 168, "Done"));
	}

	protected void buttonClicked(ButtonWidget button) {
		if (button.active) {
			if (button.id == 0) {
				SaveConfig.instance.forceAprilFools.setValue(!SaveConfig.instance.forceAprilFools.value());
				button.message = "Force April Fools: " + SaveConfig.instance.forceAprilFools.value();
			}
			if (button.id == 1) {
				SaveConfig.instance.convertClassicInv.setValue(!SaveConfig.instance.convertClassicInv.value());
				button.message = "Convert Classic Inventory: " + SaveConfig.instance.convertClassicInv.value();
			}
			if (button.id == 2) {
				Themes theme = SaveConfig.instance.dialogTheme.value();
				if (theme.equals(Themes.system)) SaveConfig.instance.dialogTheme.setValue(Themes.dark);
				else if (theme.equals(Themes.dark)) SaveConfig.instance.dialogTheme.setValue(Themes.light);
				else if (theme.equals(Themes.light)) SaveConfig.instance.dialogTheme.setValue(Themes.system);
				button.message = "Dialog Theme: " + SaveConfig.instance.dialogTheme.value().getName();
			}
			if (button.id == 3) ClientData.minecraft.m_6408915(this.parent);
		}
	}

	public final void render(int i, int j) {
		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		drawCenteredString(this.textRenderer, "Save Config", this.width / 2, 20, 16777215);
		super.render(i, j);
	}
}
