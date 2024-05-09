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

public final class LongTag extends Tag {
	public long tag;

	public LongTag() {
	}

	public LongTag(long l) {
		this.tag = l;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.tag = dataInput.readLong();
	}

	public byte m_7876673() {
		return 4;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}

