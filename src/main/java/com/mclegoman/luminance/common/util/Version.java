/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.common.util;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.Optional;

public class Version implements Comparable<Version> {
	private final String name;
	private final String id;
	private final int major;
	private final int minor;
	private final int patch;
	private final ReleaseType type;
	private final int build;
	private final boolean dirty;
	private final boolean hasModrinthId;
	private final String modrinthId;
	protected Version(String name, String id, int major, int minor, int patch, ReleaseType type, int build, boolean dirty, boolean hasModrinthId, String modrinthId) {
		this.name = name;
		this.id = id;
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.type = type;
		this.build = build;
		this.dirty = dirty;
		this.hasModrinthId = hasModrinthId;
		this.modrinthId = modrinthId;
	}
	// dirty should be set to false for versions released to modrinth (etc). dirty should only be set to true if you are building a version that won't get released.
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build, boolean dirty, String modrinthId) {
		return new Version(name, id, major, minor, patch, type, build, dirty, true, modrinthId);
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, boolean dirty, int build) {
		return new Version(name, id, major, minor, patch, type, build, dirty, false, "");
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build, String modrinthId) {
		return new Version(name, id, major, minor, patch, type, build, false, true, modrinthId);
	}
	public static Version create(String name, String id, int major, int minor, int patch, ReleaseType type, int build) {
		return new Version(name, id, major, minor, patch, type, build, false, false, "");
	}
	public static Version parse(ModMetadata metadata) {
		String version = metadata.version().toString();
		String[] versionData = version.split("-");
		String[] versionVer = versionData[0].split("\\.");
		String[] versionType = versionData[1].split("\\.");
		return create(metadata.name(), metadata.id(), Integer.parseInt(versionVer[0].replace(".", "")), Integer.parseInt(versionVer[1].replace(".", "")), Integer.parseInt(versionVer[2].replace(".", "")), Helper.stringToType(versionType[0].replace("-", "")), Integer.parseInt((versionType[1].replace(".", "")).split("\\+")[0]));
	}
	public String getFriendlyString(boolean full) {
		return full ? getFriendlyString() : (getType().equals(ReleaseType.RELEASE) ? String.format("%s.%s.%s", getMajor(), getMinor(), getPatch()) : getFriendlyString());
	}
	public boolean hasModrinthID() {
		return this.hasModrinthId;
	}
	public String getModrinthID() {
		return this.modrinthId;
	}
	public Optional<ModContainer> getModContainer() {
		return QuiltLoader.getModContainer(getID());
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
	public int getMajor() {
		return major;
	}
	public int getMinor() {
		return minor;
	}
	public int getPatch() {
		return patch;
	}
	public ReleaseType getType() {
		return type;
	}
	public int getBuild() {
		return build;
	}
	public boolean getDirty() {
		return dirty;
	}
	public String getFriendlyString() {
		return String.format("%s.%s.%s-%s.%s", getMajor(), getMinor(), getPatch(), Helper.releaseTypeString(getType(), Helper.TranslationType.CODE), getBuild());
	}
	public String getLoggerPrefix() {
		return String.format("[%s %s] ", getName(), getFriendlyString());
	}
	public boolean isDevelopmentBuild() {
		return !type.equals(ReleaseType.RELEASE);
	}
	@Override
	public int compareTo(Version other) {
		if (major != other.major) {
			return Integer.compare(major, other.major);
		} else if (minor != other.minor) {
			return Integer.compare(minor, other.minor);
		} else if (patch != other.patch) {
			return Integer.compare(patch, other.patch);
		} else if (type != other.type) {
			return type.compareTo(other.type);
		} else {
			return Integer.compare(build, other.build);
		}
	}
	public void sendToLog(LogType logType, String logMessage) {
		if (logType.equals(LogType.INFO)) System.out.println("[INFO] " + getLoggerPrefix() + logMessage);
		else if (logType.equals(LogType.WARN)) System.out.println("[WARN] " + getLoggerPrefix() + logMessage);
		else if (logType.equals(LogType.ERROR)) System.out.println("[ERROR] " + getLoggerPrefix() + logMessage);
		else if (logType.equals(LogType.DEBUG)) System.out.println("[DEBUG] " + getLoggerPrefix() + logMessage);
	}
}