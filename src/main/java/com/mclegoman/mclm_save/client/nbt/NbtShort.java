package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtShort extends NbtElement {
	public short value;

	public NbtShort() {
	}

	public NbtShort(short s) {
		this.value = s;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeShort(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readShort();
	}

	public final byte getType() {
		return 2;
	}

	public final String toString() {
		return "" + this.value;
	}
}
