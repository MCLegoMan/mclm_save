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
import java.util.HashMap;
import java.util.Map;

public final class TagCompound extends Tag {
	private final Map<String, Object> elements = new HashMap<>();

	public Map<String, Object> getElements() {
		return this.elements;
	}

	public TagCompound() {
	}

	void output(DataOutput dataOutput) throws IOException {

		for (Object tag : this.elements.values()) {
			Tag.output((Tag) tag, dataOutput);
		}

		dataOutput.writeByte(0);
	}

	void m_3656336(DataInput dataInput) throws IOException {
		this.elements.clear();

		Tag tag;
		while((tag = Tag.input(dataInput)).m_7876673() != 0) {
			this.elements.put(tag.m_1430143(), tag);
		}

	}

	public byte m_7876673() {
		return 10;
	}

	public void addNbt(String string, Tag tag) {
		this.elements.put(string, tag.m_3520709(string));
	}

	public void m_9599287(String string, byte b) {
		this.elements.put(string, (new ByteTag(b)).m_3520709(string));
	}

	public void m_4087969(String string, short s) {
		this.elements.put(string, (new ShortTag(s)).m_3520709(string));
	}

	public void m_2604386(String string, int i) {
		this.elements.put(string, (new IntTag(i)).m_3520709(string));
	}

	public void m_7606620(String string, long l) {
		this.elements.put(string, (new LongTag(l)).m_3520709(string));
	}

	public void m_0744548(String string, float f) {
		this.elements.put(string, (new FloatTag(f)).m_3520709(string));
	}

	public void m_3881827(String string, String string2) {
		this.elements.put(string, (new StringTag(string2)).m_3520709(string));
	}

	public void m_2915076(String string, byte[] bs) {
		this.elements.put(string, (new ByteArrayTag(bs)).m_3520709(string));
	}

	public void m_0738578(String string, TagCompound nbtCompound) {
		this.elements.put(string, nbtCompound.m_3520709(string));
	}

	public byte getByte(String string) {
		return !this.elements.containsKey(string) ? 0 : ((ByteTag)this.elements.get(string)).tag;
	}

	public short getShort(String string) {
		return !this.elements.containsKey(string) ? 0 : ((ShortTag)this.elements.get(string)).tag;
	}

	public int getInt(String string) {
		return !this.elements.containsKey(string) ? 0 : ((IntTag)this.elements.get(string)).tag;
	}

	public long m_6044735(String string) {
		return !this.elements.containsKey(string) ? 0L : ((LongTag)this.elements.get(string)).tag;
	}

	public float m_0000382(String string) {
		return !this.elements.containsKey(string) ? 0.0F : ((FloatTag)this.elements.get(string)).tag;
	}

	public String getString(String string) {
		return !this.elements.containsKey(string) ? "" : ((StringTag)this.elements.get(string)).tag;
	}

	public byte[] m_5601145(String string) {
		return !this.elements.containsKey(string) ? new byte[0] : ((ByteArrayTag)this.elements.get(string)).tag;
	}

	public TagCompound getNbt(String string) {
		return !this.elements.containsKey(string) ? new TagCompound() : (TagCompound)this.elements.get(string);
	}

	public TagList getList(String string) {
		return !this.elements.containsKey(string) ? new TagList() : (TagList)this.elements.get(string);
	}

	public String toString() {
		return this.elements.size() + " entries";
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
}
