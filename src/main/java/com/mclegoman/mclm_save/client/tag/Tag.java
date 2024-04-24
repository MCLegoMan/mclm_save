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

public abstract class Tag {
	private String f_2921434 = null;

	public Tag() {
	}

	abstract void output(DataOutput dataOutput) throws IOException;

	abstract void m_3656336(DataInput dataInput) throws IOException;

	public abstract byte m_7876673();

	public final String m_1430143() {
		return this.f_2921434 == null ? "" : this.f_2921434;
	}

	public final Tag m_3520709(String string) {
		this.f_2921434 = string;
		return this;
	}

	public static Tag input(DataInput dataInput) throws IOException {
		byte var1;
		if ((var1 = dataInput.readByte()) == 0) {
			return new EndTag();
		} else {
			Tag var3 = get(var1);
			byte[] var2 = new byte[dataInput.readShort()];
			dataInput.readFully(var2);
			var3.f_2921434 = new String(var2, StandardCharsets.UTF_8);
			var3.m_3656336(dataInput);
			return var3;
		}
	}

	public static void output(Tag tag, DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(tag.m_7876673());
		if (tag.m_7876673() != 0) {
			byte[] var2 = tag.m_1430143().getBytes(StandardCharsets.UTF_8);
			dataOutput.writeShort(var2.length);
			dataOutput.write(var2);
			tag.output(dataOutput);
		}
	}

	public static Tag get(byte b) {
		switch (b) {
			case 0:
				return new EndTag();
			case 1:
				return new ByteTag();
			case 2:
				return new ShortTag();
			case 3:
				return new IntTag();
			case 4:
				return new LongTag();
			case 5:
				return new FloatTag();
			case 6:
				return new DoubleTag();
			case 7:
				return new ByteArrayTag();
			case 8:
				return new StringTag();
			case 9:
				return new TagList();
			case 10:
				return new TagCompound();
			default:
				return null;
		}
	}
}
