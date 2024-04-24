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

public final class ByteTag extends Tag {
	public byte tag;

	public ByteTag() {
	}

	public ByteTag(byte b) {
		this.tag = b;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.tag = dataInput.readByte();
	}

	public byte m_7876673() {
		return 1;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
