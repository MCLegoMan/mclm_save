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

public final class ShortTag extends Tag {
	public short tag;

	public ShortTag() {
	}

	public ShortTag(short s) {
		this.tag = s;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeShort(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.tag = dataInput.readShort();
	}

	public byte m_7876673() {
		return 2;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
