/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.common.util;

public class Couple {
	private final Object first;
	private final Object second;
	public Couple(Object first, Object second) {
		this.first = first;
		this.second = second;
	}
	public Object getFirst() {
		return first;
	}
	public Object getSecond() {
		return second;
	}
}