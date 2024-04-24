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
import java.util.ArrayList;
import java.util.List;

public final class TagList extends Tag {
	private List<Tag> f_4282966 = new ArrayList<>();
	private byte f_1918247;

	public TagList() {
	}

	void output(DataOutput dataOutput) throws IOException {
		if (this.f_4282966.size() > 0) {
			this.f_1918247 = this.f_4282966.get(0).m_7876673();
		} else {
			this.f_1918247 = 1;
		}

		dataOutput.writeByte(this.f_1918247);
		dataOutput.writeInt(this.f_4282966.size());

		for(int var2 = 0; var2 < this.f_4282966.size(); ++var2) {
			this.f_4282966.get(var2).output(dataOutput);
		}

	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.f_1918247 = dataInput.readByte();
		int var2 = dataInput.readInt();
		this.f_4282966 = new ArrayList<>();

		for(int var3 = 0; var3 < var2; ++var3) {
			Tag var4 = Tag.get(this.f_1918247);
			var4.m_3656336(dataInput);
			this.f_4282966.add(var4);
		}

	}

	public byte m_7876673() {
		return 9;
	}

	public String toString() {
		StringBuilder var10000 = (new StringBuilder()).append(this.f_4282966.size()).append(" entries of type ");
		String var10001;
		switch (this.f_1918247) {
			case 0:
				var10001 = "TAG_End";
				break;
			case 1:
				var10001 = "TAG_Byte";
				break;
			case 2:
				var10001 = "TAG_Short";
				break;
			case 3:
				var10001 = "TAG_Int";
				break;
			case 4:
				var10001 = "TAG_Long";
				break;
			case 5:
				var10001 = "TAG_Float";
				break;
			case 6:
				var10001 = "TAG_Double";
				break;
			case 7:
				var10001 = "TAG_Byte_Array";
				break;
			case 8:
				var10001 = "TAG_String";
				break;
			case 9:
				var10001 = "TAG_List";
				break;
			case 10:
				var10001 = "TAG_Compound";
				break;
			default:
				var10001 = "UNKNOWN";
		}

		return var10000.append(var10001).toString();
	}

	public void addNbt(Tag tag) {
		this.f_1918247 = tag.m_7876673();
		this.f_4282966.add(tag);
	}

	public Tag getNbt(int i) {
		return this.f_4282966.get(i);
	}

	public int index() {
		return this.f_4282966.size();
	}
}
