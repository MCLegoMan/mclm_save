package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtByte extends NbtElement {
	public byte value;

	public NbtByte() {
	}

	public NbtByte(byte b) {
		this.value = b;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readByte();
	}

	public final byte getType() {
		return 1;
	}

	public final String toString() {
		return "" + this.value;
	}
}
