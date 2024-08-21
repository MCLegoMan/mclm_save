package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.nbt.NbtCompound;
import com.mclegoman.mclm_save.client.nbt.NbtElement;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SaveC_0877775 {
	public static NbtCompound m_1070817(InputStream inputStream) throws IOException {
		DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(inputStream));

		NbtCompound var5;
		try {
			NbtElement var1;
			if (!((var1 = NbtElement.deserialize(dataInputStream)) instanceof NbtCompound)) {
				throw new IOException("Root tag must be a named compound tag");
			}

			var5 = (NbtCompound)var1;
		} finally {
			dataInputStream.close();
		}

		return var5;
	}

	public static void outputNbt(NbtCompound nbtCompound, OutputStream outputStream) throws IOException {
		try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream))) {
			NbtElement.serialize(nbtCompound, dataOutputStream);
		}
	}
}
