package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtDouble extends NbtElement {
	public double value;

	public NbtDouble() {
	}

	public NbtDouble(double d) {
		this.value = d;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readDouble();
	}

	public final byte getType() {
		return 6;
	}

	public final String toString() {
		return "" + this.value;
	}
}