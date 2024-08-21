package com.mclegoman.mclm_save.client.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

public final class FileNameFilter extends FileFilter {
	private final String description;
	private final String[] filenames;
	private final String[] lowerCaseFilenames;
	public FileNameFilter(String description, String... filenames) {
		if (filenames == null || filenames.length == 0) {
			throw new IllegalArgumentException(
					"Filenames must be non-null and not empty");
		}
		this.description = description;
		this.filenames = new String[filenames.length];
		this.lowerCaseFilenames = new String[filenames.length];
		for (int i = 0; i < filenames.length; i++) {
			if (filenames[i] == null || filenames[i].length() == 0) {
				throw new IllegalArgumentException(
						"Each filename must be non-null and not empty");
			}
			this.filenames[i] = filenames[i];
			lowerCaseFilenames[i] = filenames[i].toLowerCase(Locale.ENGLISH);
		}
	}
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			for (String desiredFileName : lowerCaseFilenames) {
				if (f.getName().toLowerCase().equals(desiredFileName)) return true;
			}
		}
		return false;
	}
	public String getDescription() {
		return description;
	}
	public String[] getFilenames() {
		String[] result = new String[filenames.length];
		System.arraycopy(filenames, 0, result, 0, filenames.length);
		return result;
	}
	public String toString() {
		return super.toString() + "[description=" + getDescription() +
				" extensions=" + java.util.Arrays.asList(getFilenames()) + "]";
	}
}
