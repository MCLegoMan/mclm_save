package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NbtElement {
	private String name = null;

	public NbtElement() {
	}

	abstract void write(DataOutput dataOutput) throws IOException;

	abstract void read(DataInput dataInput) throws IOException;

	public abstract byte getType();

	public final String getName() {
		return this.name == null ? "" : this.name;
	}

	public final NbtElement m_4457344(String string) {
		this.name = string;
		return this;
	}

	public static NbtElement deserialize(DataInput dataInput) throws IOException {
		byte var1;
		if ((var1 = dataInput.readByte()) == 0) {
			return new NbtEnd();
		} else {
			NbtElement var3 = create(var1);
			byte[] var2 = new byte[dataInput.readShort()];
			dataInput.readFully(var2);
			var3.name = new String(var2, "UTF-8");
			var3.read(dataInput);
			return var3;
		}
	}

	public static void serialize(NbtElement nbtElement, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(nbtElement.getType());
		if (nbtElement.getType() != 0) {
			byte[] var2 = nbtElement.getName().getBytes("UTF-8");
			dataOutput.writeShort(var2.length);
			dataOutput.write(var2);
			nbtElement.write(dataOutput);
		}
	}

	public static NbtElement create(byte b) {
		switch (b) {
			case 0:
				return new NbtEnd();
			case 1:
				return new NbtByte();
			case 2:
				return new NbtShort();
			case 3:
				return new NbtInt();
			case 4:
				return new NbtLong();
			case 5:
				return new NbtFloat();
			case 6:
				return new NbtDouble();
			case 7:
				return new NbtByteArray();
			case 8:
				return new NbtString();
			case 9:
				return new NbtList();
			case 10:
				return new NbtCompound();
			default:
				return null;
		}
	}
}
