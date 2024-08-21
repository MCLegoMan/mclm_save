package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtInt extends NbtElement {
	public int value;

	public NbtInt() {
	}

	public NbtInt(int i) {
		this.value = i;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readInt();
	}

	public final byte getType() {
		return 3;
	}

	public final String toString() {
		return "" + this.value;
	}
}
