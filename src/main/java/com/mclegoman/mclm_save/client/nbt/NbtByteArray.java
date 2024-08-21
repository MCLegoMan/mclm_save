package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtByteArray extends NbtElement {
	public byte[] value;

	public NbtByteArray() {
	}

	public NbtByteArray(byte[] bs) {
		this.value = bs;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.value.length);
		dataOutput.write(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		int var2 = dataInput.readInt();
		this.value = new byte[var2];
		dataInput.readFully(this.value);
	}

	public final byte getType() {
		return 7;
	}

	public final String toString() {
		return "[" + this.value.length + " bytes]";
	}
}

