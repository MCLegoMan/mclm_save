/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

// This class is a modified version of ArrayField, made by MCLegoMan for mclm_save.

package me.bluecrab2.classicexplorer.fields;

import me.bluecrab2.classicexplorer.io.Reader;

import java.io.IOException;

public class BlocksField extends Field {
	String blocksDescriptor;
	public byte[] blocksContents;

	public BlocksField(String iFieldName, String iFieldValue) {
		fieldName = iFieldName;
		blocksDescriptor = iFieldValue;
	}

	@Override
	public Object getField() {
		return blocksDescriptor + " (ByteArray)";
	}

	public byte[] getBlocks() {
		return blocksContents;
	}

	@Override
	public void read() throws IOException {
		//Read the newArray from the grammar
		int tcArray = Reader.din.readUnsignedByte();
		if(tcArray != Reader.TC_ARRAY) {
			for(int i = 0; i < 10; i++) {
				System.out.println(Reader.din.readUnsignedByte() + ",");
			}
			throw new IllegalArgumentException("Invalid starter of array. Was: " + tcArray);
		}

		//Read classDesc
		Class c = Reader.readClassDesc();

		//Read
		Reader.handles.add(c);

		//Get the type of field from the second character (first is [)
		char type = blocksDescriptor.charAt(1);

		//Get the array size
		int count = Reader.din.readInt();

		if('B' == type) {
			blocksContents = new byte[count];
			for(int i = 0; i < count; i++) {
				blocksContents[i] = Reader.din.readByte();
			}
		} else {
			// This should only ever be a byte.
			throw new IllegalArgumentException("Invalid array descriptor. Was: " + type);
		}
	}
	@Override
	public Field clone() {
		return null;
	}
}
