package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtLong extends NbtElement {
	public long value;

	public NbtLong() {
	}

	public NbtLong(long l) {
		this.value = l;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readLong();
	}

	public final byte getType() {
		return 4;
	}

	public final String toString() {
		return "" + this.value;
	}
}
