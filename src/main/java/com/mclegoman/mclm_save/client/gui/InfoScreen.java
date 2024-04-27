/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.common.data.Data;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;

public final class InfoScreen extends Screen {
	private String title;
	private String status;
	private Type type;
	public boolean canBeClosed;
	public InfoScreen(String title, String status, Type type, boolean canBeClosed) {
		this.title = title;
		this.status = status;
		this.type = type;
		this.canBeClosed = canBeClosed;
	}
	public void render(int i, int j) {
		if (this.type == Type.DIRT) {
			BufferBuilder var4 = BufferBuilder.INSTANCE;
			int var5 = ClientData.minecraft.f_9413506.load("/dirt.png");
			int var8 = ClientData.minecraft.f_0545414 * 240 / ClientData.minecraft.f_5990000;
			int var3 = ClientData.minecraft.f_5990000 * 240 / ClientData.minecraft.f_5990000;
			GL11.glBindTexture(3553, var5);
			var4.start();
			var4.color(4210752);
			var4.vertex(0.0F, (float)var3, 0.0F, 0.0F, (float)var3 / 32.0F);
			var4.vertex((float)var8, (float)var3, 0.0F, (float)var8 / 32.0F, (float)var3 / 32.0F);
			var4.vertex((float)var8, 0.0F, 0.0F, (float)var8 / 32.0F, 0.0F);
			var4.vertex(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			var4.end();
		}
		if (this.type == Type.ERROR) {
			fillGradient(0, 0, this.width, this.height, -12574688, -11530224);
		}
		drawCenteredString(this.textRenderer, title, this.width / 2, 90, 16777215);
		drawCenteredString(this.textRenderer, status, this.width / 2, 110, 16777215);
		if (this.canBeClosed) drawCenteredString(this.textRenderer, "Press ESC to return to the game", this.width / 2, this.height - 20, 16777215);
		if (Data.version.isDevelopmentBuild()) {
			textRenderer.drawWithShadow(Data.version.getName() + " " + Data.version.getFriendlyString(), 2, this.height - 23, 16777215);
			textRenderer.drawWithShadow("Development Build", 2, this.height - 12, 0xFFAA00);
		}
		super.render(i, j);
	}
	public void keyPressed(char chr, int key) {
		if (this.canBeClosed) {
			if (key == 1) {
				ClientData.minecraft.m_6408915(null);
				ClientData.minecraft.m_5690108();
			}
		}
	}
	public enum Type {
		DIRT,
		ERROR
	}
}
