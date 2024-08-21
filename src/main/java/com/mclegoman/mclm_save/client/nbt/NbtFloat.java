package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtFloat extends NbtElement {
	public float value;

	public NbtFloat() {
	}

	public NbtFloat(float f) {
		this.value = f;
	}

	final void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeFloat(this.value);
	}

	final void read(DataInput dataInput) throws IOException {
		this.value = dataInput.readFloat();
	}

	public final byte getType() {
		return 5;
	}

	public final String toString() {
		return "" + this.value;
	}
}
