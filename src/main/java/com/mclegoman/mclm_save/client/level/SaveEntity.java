package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtDouble;
import com.mclegoman.mclm_save.client.nbt.NbtFloat;
import com.mclegoman.mclm_save.client.nbt.NbtList;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class SaveEntity {
	public static NbtList toNbtList(double... values) {
		NbtList list = new NbtList();
		for (double value : values) {
			list.add(new NbtDouble(value));
		}
		return list;
	}
	public static NbtList toNbtList(float... values) {
		NbtList list = new NbtList();
		for (float value : values) {
			list.add(new NbtFloat(value));
		}
		return list;
	}
	public static class BlockEntity {
		private static final Map<String, Class<?>> idToType = new HashMap<>();
		private static final Map<Class<?>, String> typeToId = new HashMap<>();
		public static String getId(Class<?> block) {
			return typeToId.get(block);
		}
		public static Class<?> getClass(String block) {
			return idToType.get(block);
		}
		private static void register(Class<?> class_, String string) {
			idToType.put(string, class_);
			typeToId.put(class_, string);
		}
		static {
			register(FurnaceBlockEntity.class, "Furnace");
			register(ChestBlockEntity.class, "Chest");
		}
	}
}
