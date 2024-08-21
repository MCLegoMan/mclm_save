package com.mclegoman.mclm_save.client.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NbtList extends NbtElement {
	private List elements = new ArrayList();
	private byte type;

	public NbtList() {
	}

	final void write(DataOutput dataOutput) throws IOException {
		if (this.elements.size() > 0) {
			this.type = ((NbtElement)this.elements.get(0)).getType();
		} else {
			this.type = 1;
		}

		dataOutput.writeByte(this.type);
		dataOutput.writeInt(this.elements.size());

		for(int var2 = 0; var2 < this.elements.size(); ++var2) {
			((NbtElement)this.elements.get(var2)).write(dataOutput);
		}

	}

	final void read(DataInput dataInput) throws IOException {
		this.type = dataInput.readByte();
		int var2 = dataInput.readInt();
		this.elements = new ArrayList();

		for(int var3 = 0; var3 < var2; ++var3) {
			NbtElement var4;
			(var4 = NbtElement.create(this.type)).read(dataInput);
			this.elements.add(var4);
		}

	}

	public final byte getType() {
		return 9;
	}

	public final String toString() {
		StringBuilder var10000 = (new StringBuilder()).append("").append(this.elements.size()).append(" entries of type ");
		String var10001;
		switch (this.type) {
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

	public final void add(NbtElement nbtElement) {
		this.type = nbtElement.getType();
		this.elements.add(nbtElement);
	}

	public final NbtElement get(int i) {
		return (NbtElement)this.elements.get(i);
	}

	public final int size() {
		return this.elements.size();
	}
}

