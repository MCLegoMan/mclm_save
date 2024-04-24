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

public final class DoubleTag extends Tag {
	private double f_3515327;

	public DoubleTag() {
	}

	void output(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(this.f_3515327);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.f_3515327 = dataInput.readDouble();
	}

	public byte m_7876673() {
		return 6;
	}

	public String toString() {
		return String.valueOf(this.f_3515327);
	}
}
