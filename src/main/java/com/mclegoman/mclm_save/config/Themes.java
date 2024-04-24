/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.config;

public enum Themes {
	dark("Dark (FlatDarculaLaf)"),
	light("Light (FlatIntelliJLaf)"),
	system("System");
	final String name;
	Themes(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
