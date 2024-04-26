/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.tag.*;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.Data;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.unmapped.C_2411117;
import net.minecraft.world.World;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public abstract class Level {
	private final C_2411117 f_1154694;

	public Level(C_2411117 c_2411117) {
		this.f_1154694 = c_2411117;
	}

	public final World load(InputStream inputStream) throws IOException {
		if (this.f_1154694 != null) {
			this.f_1154694.m_0983733("Loading level");
		}

		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Reading..");
		}

		TagCompound inputStream1 = TagCompoundStream.toTagCompound(inputStream);
		TagCompound var3 = inputStream1.getNbt("Map");
		TagCompound var4 = inputStream1.getNbt("Environment");
		TagList var5 = inputStream1.getList("Entities");
		int var6 = var3.getShort("Width");
		short var7 = var3.getShort("Length");
		short var8 = var3.getShort("Height");
		World var9 = new World();
		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Preparing level..");
		}

		TagList var10 = var3.getList("Spawn");
		var9.f_3926541 = ((ShortTag)var10.getNbt(0)).tag;
		var9.f_2923303 = ((ShortTag)var10.getNbt(1)).tag;
		var9.f_8500813 = ((ShortTag)var10.getNbt(2)).tag;
		var9.f_1709243 = var4.getInt("CloudColor");
		var9.f_3766825 = var4.getInt("SkyColor");
		var9.f_2946178 = var4.getInt("FogColor");
		var9.f_6732352 = (float)var4.getByte("SkyBrightness") / 100.0F;
		var9.f_4971921 = var4.getShort("CloudHeight");
		var9.f_0183464 = var4.getShort("SurroundingGroundHeight");
		var9.f_8873427 = var4.getShort("SurroundingWaterHeight");
		var9.f_3241378 = var4.getByte("SurroundingWaterType");
		var9.m_2817546(var6, var8, var7, var3.m_5601145("Blocks"));
		if (this.f_1154694 != null) {
			this.f_1154694.m_1154571("Preparing entities..");
		}
		for(int var13 = 0; var13 < var5.index(); ++var13) {
			String var16 = (var3 = (TagCompound)var5.getNbt(var13)).getString("id");
			Entity var19;
			if ((var19 = this.getEntity(var9, var16)) != null) {
				this.loadEntityData(var3, var19);
			}
		}

		return var9;
	}

	protected Entity getEntity(World world, String string) {
		return null;
	}

	public final void save(World world, File file) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		TagCompound level = LevelFile.createLevel(world.f_1709243, (short) world.f_4971921, world.f_2946178, (byte) (world.f_6732352 * 100.0F), (int) world.f_3766825, (short) world.f_0183464, (byte) Block.GRASS.id, (short) world.f_8873427, (byte) world.f_3241378, (short) world.f_3926541, (short) world.f_2923303, (short) world.f_8500813, (short) world.f_4184003, (short) world.f_8212213, (short) world.f_3061106, Accessors.World.f_4249554);
		TagList entities = new TagList();
		for (Object entity : world.f_7148360.f_6899876) {
			Entity currentEntity = (Entity)entity;
			currentEntity.m_2914294(currentEntity);
			TagCompound entityData = saveEntityData(currentEntity);
			if (!entityData.isEmpty()) entities.addNbt(entityData);
		}
		level.addNbt("Entities", entities);
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
			Tag.output(level, new DataOutputStream(gzipOutputStream));
			gzipOutputStream.close();
		} catch (Exception error) {
			Data.version.sendToLog(Helper.LogType.WARN, error.getLocalizedMessage());
		} finally {
			outputStream.close();
		}
	}
	public final void loadEntityData(TagCompound nbtCompound, Entity entity) {
		TagList var2 = nbtCompound.getList("Pos");
		TagList var3 = nbtCompound.getList("Motion");
		TagList var4 = nbtCompound.getList("Rotation");
		entity.x = ((FloatTag)var2.getNbt(0)).f_0036635;
		entity.y = ((FloatTag)var2.getNbt(1)).f_0036635;
		entity.z = ((FloatTag)var2.getNbt(2)).f_0036635;
		entity.velocityX = ((FloatTag)var3.getNbt(0)).f_0036635;
		entity.velocityY = ((FloatTag)var3.getNbt(1)).f_0036635;
		entity.velocityZ = ((FloatTag)var3.getNbt(2)).f_0036635;
		entity.yaw = ((FloatTag)var4.getNbt(0)).f_0036635;
		entity.pitch = ((FloatTag)var4.getNbt(1)).f_0036635;
		Accessors.getEntity(entity).setFallDistance(nbtCompound.m_0000382("FallDistance"));
		entity.onFireTimer = nbtCompound.getShort("Fire");
		if (entity instanceof LivingEntity) ((LivingEntity) entity).f_0554371 = nbtCompound.getShort("Air");
		entity.refreshPositionAndAngles(entity.x, entity.y, entity.z, entity.yaw, entity.pitch);
		if (entity instanceof PlayerEntity) {
			TagList inventory = nbtCompound.getList("Inventory");
			PlayerInventory playerInventory = new PlayerInventory();
			for(int var13 = 0; var13 < inventory.index(); ++var13) {
				TagCompound index = (TagCompound)inventory.getNbt(var13);
				int count = index.getByte("Count");
				int id = index.getShort("id");
				int slot = index.getByte("Slot");
				setStack(playerInventory.inventorySlots, slot, new ItemStack(id, count));
			}
			((PlayerEntity)entity).inventory = playerInventory;
			Accessors.getPlayerEntity((PlayerEntity)entity).setPlayerScore(nbtCompound.getInt("Score"));
		}
	}
	public final TagCompound saveEntityData(Entity entity) {
		TagCompound nbtCompound = new TagCompound();
		TagList motion = new TagList();
		motion.addNbt(new FloatTag(entity.velocityX));
		motion.addNbt(new FloatTag(entity.velocityY));
		motion.addNbt(new FloatTag(entity.velocityZ));
		nbtCompound.addNbt("Motion", motion);
		TagList pos = new TagList();
		pos.addNbt(new FloatTag(entity.x));
		pos.addNbt(new FloatTag(entity.y));
		pos.addNbt(new FloatTag(entity.z));
		nbtCompound.addNbt("Pos", pos);
		TagList rotation = new TagList();
		rotation.addNbt(new FloatTag(entity.yaw));
		rotation.addNbt(new FloatTag(entity.pitch));
		nbtCompound.addNbt("Rotation", rotation);
		nbtCompound.addNbt("FallDistance", new FloatTag(Accessors.getEntity(entity).getFallDistance()));
		nbtCompound.addNbt("Fire", new ShortTag((short) entity.onFireTimer));
		if (entity instanceof PlayerEntity) {
			nbtCompound.addNbt("id", new StringTag("LocalPlayer"));
			TagList inventory = new TagList();
			for(int invSlot = 0; invSlot < ((PlayerEntity)entity).inventory.inventorySlots.length; ++invSlot) {
				TagCompound inventorySlot = new TagCompound();
				ItemStack stack = ((PlayerEntity)entity).inventory.inventorySlots[invSlot];
				if (stack != null) {
					inventorySlot.addNbt("Count", new ByteTag((byte) stack.size));
					inventorySlot.addNbt("id", new ShortTag((short) stack.itemId));
					inventorySlot.addNbt("Slot", new ByteTag((byte) invSlot));
					inventory.addNbt(inventorySlot);
				}
			}
			nbtCompound.addNbt("Inventory", inventory);
			nbtCompound.addNbt("Score", new IntTag(Accessors.getPlayerEntity((PlayerEntity)entity).getPlayerScore()));
		} else if (entity instanceof LivingEntity) {
			nbtCompound.addNbt("id", new StringTag("Mob"));
			nbtCompound.addNbt("Air", new IntTag(((LivingEntity)entity).f_0554371));
		} else if (entity instanceof ItemEntity) {
			nbtCompound.addNbt("id", new StringTag("Item"));
		}
		return nbtCompound;
	}
	public final void setStack(ItemStack[] inventorySlots, int slot, ItemStack stack) {
		inventorySlots[slot] = stack;
	}
}
