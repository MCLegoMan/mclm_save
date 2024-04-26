/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.config;

import com.mclegoman.mclm_save.common.Data;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class SaveConfig extends ReflectiveConfig {
	public static final SaveConfig instance = QuiltConfig.create(Data.version.getID(), Data.version.getID(), SaveConfig.class);
	@Comment("When set to true, the game will think it's always april fools'.")
	public final TrackedValue<Boolean> forceAprilFools = this.value(false);
	@Comment("When set to true, the game will think it's always april fools'.")
	public final TrackedValue<Boolean> convertClassicInv = this.value(false);
	@Comment("This sets the theme of the save/load dialog")
	public final TrackedValue<Theme> dialogTheme = this.value(Theme.system);
	@Comment("This sets where the save/load dialog opens")
	public final TrackedValue<String> dialogDir = this.value(QuiltLoader.getGameDir().toString());
}
