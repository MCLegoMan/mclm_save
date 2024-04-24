/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ByteArrayTag extends Tag {
	public byte[] f_7219938;

	public ByteArrayTag() {
	}

	public ByteArrayTag(byte[] bs) {
		this.f_7219938 = bs;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.f_7219938.length);
		dataOutput.write(this.f_7219938);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		int var2 = dataInput.readInt();
		this.f_7219938 = new byte[var2];
		dataInput.readFully(this.f_7219938);
	}

	public byte m_7876673() {
		return 7;
	}

	public String toString() {
		return "[" + this.f_7219938.length + " bytes]";
	}
}
