/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.common;

import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.version.Helper;
import com.mclegoman.releasetypeutils.common.version.Version;

import java.util.logging.Logger;

public class Data {
	public static Version version = new Version("Save", "mclm_save", 1, 0, 0, Helper.ReleaseType.BETA, 1);
}
