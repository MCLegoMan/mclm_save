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
import java.nio.charset.StandardCharsets;

public final class StringTag extends Tag {
	public String f_4042017;

	public StringTag() {
	}

	public StringTag(String string) {
		this.f_4042017 = string;
	}

	void output(DataOutput dataOutput) throws IOException {
		byte[] var2 = this.f_4042017.getBytes(StandardCharsets.UTF_8);
		dataOutput.writeShort(var2.length);
		dataOutput.write(var2);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		byte[] var2 = new byte[dataInput.readShort()];
		dataInput.readFully(var2);
		this.f_4042017 = new String(var2, StandardCharsets.UTF_8);
	}

	public byte m_7876673() {
		return 8;
	}

	public String toString() {
		return this.f_4042017;
	}
}
