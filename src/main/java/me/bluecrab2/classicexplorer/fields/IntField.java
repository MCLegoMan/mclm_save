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

/** Field for an int primitive */
public class IntField extends Field {
	int fieldValue;
	
	public IntField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Integer) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readInt();
	}

	@Override
	public IntField clone() {
		return new IntField(fieldName);
	}
}
