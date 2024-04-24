/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.Entity;

public final class World extends Level {
	public World() {
		super(ClientData.minecraft.f_7424826);
	}
	protected Entity getEntity(net.minecraft.world.World world, String string) {
		return string.equals("LocalPlayer") ? new InputPlayerEntity(world) : super.getEntity(world, string);
	}
}
