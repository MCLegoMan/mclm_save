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

public final class FloatTag extends Tag {
	public float tag;

	public FloatTag() {
	}

	public FloatTag(float f) {
		this.tag = f;
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeFloat(this.tag);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.tag = dataInput.readFloat();
	}

	public byte m_7876673() {
		return 5;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
