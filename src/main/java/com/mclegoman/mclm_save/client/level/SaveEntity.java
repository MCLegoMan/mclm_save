package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtDouble;
import com.mclegoman.mclm_save.client.nbt.NbtList;

public interface SaveEntity {
	void mclm_save$writeEntityNbt(NbtCompound nbtCompound);
	void mclm_save$readCustomNbt(NbtCompound nbtCompound);
	void mclm_save$writeCustomNbt(NbtCompound nbtCompound);
	String mclm_save$entityId();
	default NbtList mclm_save$toNbtList(double... values) {
		NbtList list = new NbtList();
		for (double value : values) {
			list.add(new NbtDouble(value));
		}
		return list;
	}
}
