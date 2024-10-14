/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.common.util;

public class Helper {
	public static ReleaseType stringToType(String type) {
		if (type.equalsIgnoreCase("alpha")) return ReleaseType.ALPHA;
		if (type.equalsIgnoreCase("beta")) return ReleaseType.BETA;
		if (type.equalsIgnoreCase("rc")) return ReleaseType.RELEASE_CANDIDATE;
		else return ReleaseType.RELEASE;
	}
	public static String setString(String string, Object... variables) {
		String RETURN = string;
		for (Object variable : variables) RETURN = replaceFirst(RETURN, "{}", (String)variable);
		return RETURN;
	}
	public static String replaceFirst(String s, String pattern, String replacement) {
		int idx = s.indexOf(pattern);
		return s.substring(0, idx) + replacement + s.substring(idx + pattern.length());
	}
	public static String releaseTypeString(ReleaseType releaseType, TranslationType translationType) {
		if (releaseType.equals(ReleaseType.ALPHA)) {
			if (translationType.equals(TranslationType.NORMAL)) return "Alpha";
			else if (translationType.equals(TranslationType.SENTENCED)) return "an Alpha";
			else if (translationType.equals(TranslationType.CODE)) return "alpha";
		} else if (releaseType.equals(ReleaseType.BETA)) {
			if (translationType.equals(TranslationType.NORMAL)) return "Beta";
			else if (translationType.equals(TranslationType.SENTENCED)) return "a Beta";
			else if (translationType.equals(TranslationType.CODE)) return "beta";
		} else if (releaseType.equals(ReleaseType.RELEASE_CANDIDATE)) {
			if (translationType.equals(TranslationType.NORMAL)) return "Release Candidate";
			else if (translationType.equals(TranslationType.SENTENCED)) return "a Release Candidate";
			else if (translationType.equals(TranslationType.CODE)) return "rc";
		} else if (releaseType.equals(ReleaseType.RELEASE)) {
			if (translationType.equals(TranslationType.NORMAL)) return "Release";
			else if (translationType.equals(TranslationType.SENTENCED)) return "a Release";
			else if (translationType.equals(TranslationType.CODE)) return "release";
		}
		return "unknown";
	}
	public enum TranslationType {
		NORMAL,
		SENTENCED,
		CODE
	}
}
