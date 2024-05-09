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

/** Field for a boolean primitive */
public class BooleanField extends Field {
	boolean fieldValue;
	
	public BooleanField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Boolean) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readBoolean();
	}
	
	@Override
	public BooleanField clone() {
		return new BooleanField(fieldName);
	}
}
