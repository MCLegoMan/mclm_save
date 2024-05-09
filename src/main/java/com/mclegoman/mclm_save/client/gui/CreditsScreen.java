/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.common.util.Couple;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class CreditsScreen extends Screen {
	private final Screen parent;
	private int time;
	private final List<Couple> credits = new ArrayList<>();
	public CreditsScreen(Screen screen) {
		this.parent = screen;
	}
	public void init() {
		this.credits.add(new Couple("Save", 0xFFAA00));
		this.credits.add(new Couple("License: LGPL-3.0-or-later", 0xFFFFFF));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Phantazap (Owner)", 0xFFFFFF));
		this.credits.add(new Couple("MCLegoMan (Lead Developer)", 0xFFFFFF));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Attribution", 0xFFAA00));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("ClassicExplorer", 0xFFFFFF));
		this.credits.add(new Couple("Read Minecraft world files from Classic versions", 0xAAAAAA));
		this.credits.add(new Couple("Author: bluecrab2", 0xAAAAAA));
		this.credits.add(new Couple("License: All Rights Reserved (c) bluecrab2", 0xAAAAAA));
		this.credits.add(new Couple("Included with permission from bluecrab2.", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("FlatLaf", 0xFFFFFF));
		this.credits.add(new Couple("Darcula/IntelliJ dialog themes.", 0xAAAAAA));
		this.credits.add(new Couple("Author: JFormDesigner", 0xAAAAAA));
		this.credits.add(new Couple("License: Apache-2.0", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("material-ui-swing", 0xFFFFFF));
		this.credits.add(new Couple("Material dialog theme.", 0xAAAAAA));
		this.credits.add(new Couple("Author: vincenzopalazzo", 0xAAAAAA));
		this.credits.add(new Couple("License: MIT", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Feather Mappings", 0xFFFFFF));
		this.credits.add(new Couple("Minecraft mappings for legacy versions.", 0xAAAAAA));
		this.credits.add(new Couple("Author: OrnitheMC", 0xAAAAAA));
		this.credits.add(new Couple("License: CC0-1.0", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Quilt Loader", 0xFFFFFF));
		this.credits.add(new Couple("The loader for Quilt mods.", 0xAAAAAA));
		this.credits.add(new Couple("Author: QuiltMC", 0xAAAAAA));
		this.credits.add(new Couple("License: Apache-2.0", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Minecraft", 0xFFFFFF));
		this.credits.add(new Couple("The base game.", 0xAAAAAA));
		this.credits.add(new Couple("Author: Mojang Studios", 0xAAAAAA));
		this.credits.add(new Couple("License: Minecraft EULA", 0xAAAAAA));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("Special Thanks", 0xFFAA00));
		this.credits.add(new Couple("", 0xFFFFFF));
		this.credits.add(new Couple("bluecrab2", 0xFFFFFF));
		this.credits.add(new Couple("Thank you for allowing us to include ClassicExplorer in mclm_save!", 0xAAAAAA));
	}
	public final void render(int i, int j) {
		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		credits.forEach(credit -> textRenderer.drawWithShadow((String) credit.getFirst(), (this.width / 2) - (textRenderer.getWidth((String) credit.getFirst()) / 2), (this.height + (credits.indexOf(credit) * 10)) - time, (Integer) credit.getSecond()));
		super.render(i, j);
	}
	public void keyPressed(char chr, int key) {
		if (key == 1) {
			ClientData.minecraft.m_6408915(this.parent);
		}
	}
	public void tick() {
		if (time > ((this.credits.size() * 10) + this.height)) {
			ClientData.minecraft.m_6408915(this.parent);
		} else {
			time += (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) ? (Keyboard.isKeyDown(Keyboard.KEY_UP) ? -4 : 4) : (Keyboard.isKeyDown(Keyboard.KEY_UP) ? -1 : 1);
		}
	}
}
