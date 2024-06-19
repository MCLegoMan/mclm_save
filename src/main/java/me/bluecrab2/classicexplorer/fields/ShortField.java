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

/** Field for a short primitive */
public class ShortField extends Field {
	short fieldValue;
	
	public ShortField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Short) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readShort();
	}

	@Override
	public ShortField clone() {
		return new ShortField(fieldName);
	}
}
