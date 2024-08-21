package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public final class NbtEnd extends NbtElement {
	public NbtEnd() {
	}

	final void read(DataInput dataInput) {
	}

	final void write(DataOutput dataOutput) {
	}

	public final byte getType() {
		return 0;
	}

	public final String toString() {
		return "END";
	}
}
