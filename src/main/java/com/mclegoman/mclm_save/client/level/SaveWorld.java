package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.world.World;

import java.io.File;

public class SaveWorld {
	private SaveWorldThread saveThread;
	private final File savesDir;
	private final String id;
	public long sizeOnDisk;
	private SaveWorld(File savesDir, String id) {
		this.savesDir = savesDir;
		this.id = id;
		this.sizeOnDisk = 0L;
	}
	public static class Builder {
		private File savesDir;
		private final String id;
		public Builder(String id) {
			this.savesDir = new File(SaveMinecraft.getMinecraftDir(), "saves");
			this.id = id;
		}
		public Builder savesDir(File file) {
			this.savesDir = file;
			return this;
		}
		public SaveWorld build() {
			return new SaveWorld(this.savesDir, this.id);
		}
	}
	public World getWorld() {
		World loadedWorld = ClientData.minecraft.f_5854988;
		return loadedWorld != null ? loadedWorld : new World();
	}
	public File getSavesDir() {
		return this.savesDir;
	}
	public String getId() {
		return this.id;
	}
	public File getDir() {
		return new File(getSavesDir(), getId());
	}
	public boolean getSaving() {
		return this.saveThread != null && this.saveThread.isAlive();
	}
	public void save() throws InterruptedException {
		if (getWorld() != null) {
			if (!getSaving()) {
				SaveMinecraft.ticks = 0L;
				this.saveThread = new SaveWorldThread();
				this.saveThread.start();
				new Thread(() -> {
					try {
						this.saveThread.join();
						Data.version.sendToLog(Helper.LogType.INFO, "World has been saved!");
					} catch (InterruptedException e) {
						Data.version.sendToLog(Helper.LogType.WARN, "Saving was interrupted!");
						Thread.currentThread().interrupt();
					}
				}).start();
			} else {
				Data.version.sendToLog(Helper.LogType.WARN, "World is already getting saved!");
			}
		}
	}
}
