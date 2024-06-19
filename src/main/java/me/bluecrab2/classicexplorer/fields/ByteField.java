/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

package me.bluecrab2.classicexplorer.fields;

import java.io.IOException;

import me.bluecrab2.classicexplorer.io.Reader;

/** Field for a byte primitive */
public class ByteField extends Field {
	byte fieldValue;
	
	public ByteField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Byte) fieldValue;
	}
	
	public void setField(byte b) {
		fieldValue = b;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readByte();
	}
	
	@Override
	public ByteField clone() {
		return new ByteField(fieldName);
	}
}
