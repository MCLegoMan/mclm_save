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

public final class IntTag extends Tag {
	public int tag;

	public IntTag() {
	}

	public IntTag(int i) {
		this.tag = i;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.tag = dataInput.readInt();
	}

	public byte m_7876673() {
		return 3;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
