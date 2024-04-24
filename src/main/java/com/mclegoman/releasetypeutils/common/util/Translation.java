package com.mclegoman.releasetypeutils.common.util;

import com.mclegoman.releasetypeutils.common.version.Helper;

public class Translation {
	public static String setString(String string, Object... variables) {
		String RETURN = string;
		for (Object variable : variables) RETURN = replaceFirst(RETURN, "{}", (String)variable);
		return RETURN;
	}
	public static String replaceFirst(String s, String pattern, String replacement) {
		int idx = s.indexOf(pattern);
		return s.substring(0, idx) + replacement + s.substring(idx + pattern.length());
	}
	public static String releaseTypeString(Helper.ReleaseType releaseType, ReleaseTypeTranslationType translationType) {
		if (releaseType.equals(Helper.ReleaseType.ALPHA)) {
			if (translationType.equals(ReleaseTypeTranslationType.NORMAL)) return "Alpha";
			else if (translationType.equals(ReleaseTypeTranslationType.SENTENCED)) return "an Alpha";
			else if (translationType.equals(ReleaseTypeTranslationType.CODE)) return "alpha";
		} else if (releaseType.equals(Helper.ReleaseType.BETA)) {
			if (translationType.equals(ReleaseTypeTranslationType.NORMAL)) return "Beta";
			else if (translationType.equals(ReleaseTypeTranslationType.SENTENCED)) return "a Beta";
			else if (translationType.equals(ReleaseTypeTranslationType.CODE)) return "beta";
		} else if (releaseType.equals(Helper.ReleaseType.RELEASE_CANDIDATE)) {
			if (translationType.equals(ReleaseTypeTranslationType.NORMAL)) return "Release Candidate";
			else if (translationType.equals(ReleaseTypeTranslationType.SENTENCED)) return "a Release Candidate";
			else if (translationType.equals(ReleaseTypeTranslationType.CODE)) return "rc";
		} else if (releaseType.equals(Helper.ReleaseType.RELEASE)) {
			if (translationType.equals(ReleaseTypeTranslationType.NORMAL)) return "Release";
			else if (translationType.equals(ReleaseTypeTranslationType.SENTENCED)) return "a Release";
			else if (translationType.equals(ReleaseTypeTranslationType.CODE)) return "release";
		}
		return "UNKNOWN";
	}
	public enum ReleaseTypeTranslationType {
		NORMAL,
		SENTENCED,
		CODE
	}
}
