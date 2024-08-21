package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtString extends NbtElement {
	public String value;

	public NbtString() {
	}

	public NbtString(String string) {
		this.value = string;
	}

	final void write(DataOutput dataOutput) throws IOException {
		byte[] var2 = this.value.getBytes("UTF-8");
		dataOutput.writeShort(var2.length);
		dataOutput.write(var2);
	}

	final void read(DataInput dataInput) throws IOException {
		byte[] var2 = new byte[dataInput.readShort()];
		dataInput.readFully(var2);
		this.value = new String(var2, "UTF-8");
	}

	public final byte getType() {
		return 8;
	}

	public final String toString() {
		return "" + this.value;
	}
}
