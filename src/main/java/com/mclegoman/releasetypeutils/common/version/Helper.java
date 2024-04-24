package com.mclegoman.releasetypeutils.common.version;

public class Helper {
	public static ReleaseType stringToType(String type) {
		if (type.equalsIgnoreCase("alpha")) {
			return ReleaseType.ALPHA;
		}
		if (type.equalsIgnoreCase("beta")) {
			return ReleaseType.BETA;
		}
		if (type.equalsIgnoreCase("rc")) {
			return ReleaseType.RELEASE_CANDIDATE;
		} else {
			return ReleaseType.RELEASE;
		}
	}
	public enum LogType {
		INFO,
		WARN
	}
	public enum ReleaseType {
		ALPHA,
		BETA,
		RELEASE_CANDIDATE,
		RELEASE
	}
}
