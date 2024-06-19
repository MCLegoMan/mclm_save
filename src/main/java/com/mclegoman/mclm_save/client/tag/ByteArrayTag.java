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
	public byte[] tag;

	public ByteArrayTag() {
	}

	public ByteArrayTag(byte[] bs) {
		this.tag = bs;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.tag.length);
		dataOutput.write(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		int var2 = dataInput.readInt();
		this.tag = new byte[var2];
		dataInput.readFully(this.tag);
	}

	public byte m_7876673() {
		return 7;
	}

	public String toString() {
		return "[" + this.tag.length + " bytes]";
	}
}
