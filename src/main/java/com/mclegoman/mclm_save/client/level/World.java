/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;

public final class World extends Level {
	public World() {
	}
	protected Entity getEntity(net.minecraft.world.World world, String string) {
		return string.equals("LocalPlayer") ? new PlayerEntity(world) : super.getEntity(world, string);
	}
}
