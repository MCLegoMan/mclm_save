/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.tag;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class TagCompoundStream {
	public static TagCompound toTagCompound(InputStream inputStream) throws IOException {
		DataInputStream inputStream1 = new DataInputStream(new GZIPInputStream(inputStream));

		TagCompound var5;
		try {
			Tag var1;
			if (!((var1 = Tag.input(inputStream1)) instanceof TagCompound)) {
				throw new IOException("Root tag must be a named compound tag");
			}

			var5 = (TagCompound)var1;
		} finally {
			inputStream1.close();
		}

		return var5;
	}
}
