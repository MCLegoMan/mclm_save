/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.version.Helper;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public final class InfoScreen extends Screen {
	public String title;
	public List<String> status;
	public Type type;
	public String canBeClosedMessage;
	public String extraInfoMessage;
	public InfoScreen(String title, List<String> status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		this.title = title;
		this.status = status;
		this.type = type;
		this.canBeClosedMessage = canBeClosedMessage;
		this.extraInfoMessage = extraInfoMessage;
		for (String message : status) Data.version.sendToLog(this.type.equals(Type.ERROR) ? Helper.LogType.WARN : Helper.LogType.INFO, message);
	}
	public InfoScreen(String title, String status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		this.title = title;
		List<String> messages = new ArrayList<>();
		if (status != null) {
			if (status.length() <= 64) {
				messages.add(status);
			} else {
				int index = 0;
				while (index < status.length()) {
					int newIndex = Math.min(index + 64, status.length());
					messages.add(status.substring(index, newIndex));
					index = newIndex;
				}
			}
		}
		this.status = messages;
		this.type = type;
		this.canBeClosedMessage = canBeClosedMessage;
		this.extraInfoMessage = extraInfoMessage;
		Data.version.sendToLog(this.type.equals(Type.ERROR) ? Helper.LogType.WARN : Helper.LogType.INFO, status);
	}
	public InfoScreen(String title, List<String> status, Type type, boolean canBeClosed, String extraInfoMessage) {
		this(title, status, type, canBeClosed ? "Press ESC to return to the game" : "", extraInfoMessage);
	}
	public InfoScreen(String title, List<String> status, Type type, boolean canBeClosed) {
		this(title, status, type, canBeClosed ? "Press ESC to return to the game" : "", "");
	}
	public InfoScreen(String title, String status, Type type, boolean canBeClosed, String extraInfoMessage) {
		this(title, status, type, canBeClosed ? "Press ESC to return to the game" : "", extraInfoMessage);
	}
	public InfoScreen(String title, String status, Type type, boolean canBeClosed) {
		this(title, status, type, canBeClosed ? "Press ESC to return to the game" : "", "");
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
		int y = Math.max((this.height / 2) - ((this.status.size() * 11) / 2), 22);
		drawCenteredString(this.textRenderer, title, this.width / 2, y - 20, 16777215);
		for (String string : this.status) {
			drawCenteredString(this.textRenderer, string, this.width / 2, y, 16777215);
			y += 11;
		}
		if (this.canBeClosedMessage != null && !this.canBeClosedMessage.isEmpty()) drawCenteredString(this.textRenderer, this.canBeClosedMessage, this.width / 2, this.height - 20, 16777215);
		if (this.extraInfoMessage != null && !this.extraInfoMessage.isEmpty()) drawCenteredString(this.textRenderer, this.extraInfoMessage, this.width / 2, this.height - 31, 16777215);
		if (Data.version.isDevelopmentBuild() || SaveConfig.instance.debug.value()) {
			textRenderer.drawWithShadow(Data.version.getName() + " " + Data.version.getFriendlyString() + " (" + Data.mcVersion + ")", 2, this.height - (Data.version.isDevelopmentBuild() ? 23 : 12), 16777215);
			if (Data.version.isDevelopmentBuild()) textRenderer.drawWithShadow("Development Build", 2, this.height - 12, 0xFFAA00);
		}
		super.render(i, j);
	}
	public void keyPressed(char chr, int key) {
		if (this.canBeClosedMessage != null && !this.canBeClosedMessage.isEmpty()) {
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
