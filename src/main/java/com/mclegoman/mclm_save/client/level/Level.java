/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.level;

import com.mclegoman.mclm_save.client.data.ClientData;
import com.mclegoman.mclm_save.client.tag.*;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import com.mclegoman.mclm_save.common.util.Couple;
import com.mclegoman.mclm_save.config.SaveConfig;
import com.mclegoman.releasetypeutils.common.version.Helper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.io.*;
import java.time.Instant;
import java.util.zip.GZIPOutputStream;

public abstract class Level {
//	private final C_2411117 f_1154694;
//
//	public Level(C_2411117 c_2411117) {
//		this.f_1154694 = c_2411117;
//	}

	public final World load(InputStream inputStream) throws IOException {
		Data.version.sendToLog(Helper.LogType.INFO, "Loading level");
		Data.version.sendToLog(Helper.LogType.INFO, "Reading...");
//		if (this.f_1154694 != null) {
//			this.f_1154694.m_0983733("Loading level");
//		}
//
//		if (this.f_1154694 != null) {
//			this.f_1154694.m_1154571("Reading..");
//		}

		TagCompound inputStream1 = TagCompoundStream.toTagCompound(inputStream);
		TagCompound map = inputStream1.getNbt("Map");
		TagCompound environment = inputStream1.getNbt("Environment");
		TagList entities = inputStream1.getList("Entities");
		int width = map.getShort("Width");
		short length = map.getShort("Length");
		short height = map.getShort("Height");
		World world = new World();
		Data.version.sendToLog(Helper.LogType.INFO, "Preparing level..");
//		if (this.f_1154694 != null) {
//			this.f_1154694.m_1154571("Preparing level..");
//		}

		TagList var10 = map.getList("Spawn");
		world.f_3926541 = ((ShortTag)var10.getNbt(0)).tag;
		world.f_2923303 = ((ShortTag)var10.getNbt(1)).tag;
		world.f_8500813 = ((ShortTag)var10.getNbt(2)).tag;
		world.f_1709243 = environment.getInt("CloudColor");
		world.f_3766825 = environment.getInt("SkyColor");
		world.f_2946178 = environment.getInt("FogColor");
		//world.f_6732352 = (float)environment.getByte("SkyBrightness") / 100.0F;
		//world.f_4971921 = environment.getShort("CloudHeight");
		//world.f_0183464 = environment.getShort("SurroundingGroundHeight");
		world.f_8873427 = environment.getShort("SurroundingWaterHeight");
		//world.f_3241378 = environment.getByte("SurroundingWaterType");
		byte[] blocks = map.m_5601145("Blocks");
		world.m_2817546(width, height, length, blocks);
		Data.version.sendToLog(Helper.LogType.INFO, "Preparing entities..");
//		if (this.f_1154694 != null) {
//			this.f_1154694.m_1154571("Preparing entities..");
//		}
		for(int var13 = 0; var13 < entities.index(); ++var13) {
			String var16 = (map = (TagCompound)entities.getNbt(var13)).getString("id");
			Entity var19;
			if ((var19 = this.getEntity(world, var16)) != null) {
				this.loadEntityData(map, var19);
			}
		}

		return world;
	}

	protected Entity getEntity(World world, String string) {
		return null;
	}

	public final void save(World world, File file) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		TagCompound level = LevelFile.createLevel("Player", Instant.now().getEpochSecond(), "A Nice World", world.f_1709243, (short) 66, world.f_2946178, (byte) 100, world.f_3766825, (short) 23, (byte) Block.GRASS.id, (short) world.f_8873427, (byte) 8, (short) world.f_3926541, (short) world.f_2923303, (short) world.f_8500813, (short) world.f_4184003, (short) world.f_8212213, (short) world.f_3061106, world.f_4249554);
		TagList entities = new TagList();
		for (Object entity : world.f_7148360.f_6899876) {
			Entity currentEntity = (Entity)entity;
			currentEntity.setPosition((int)currentEntity.x, (int)currentEntity.y, (int)currentEntity.z);
			TagCompound entityData = saveEntityData(currentEntity);
			if (!entityData.isEmpty() && ClientData.minecraft.f_6058446.deathTime == 0) entities.addNbt(entityData);
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
		entity.x = ((FloatTag)var2.getNbt(0)).tag;
		entity.y = ((FloatTag)var2.getNbt(1)).tag;
		entity.z = ((FloatTag)var2.getNbt(2)).tag;
		entity.velocityX = ((FloatTag)var3.getNbt(0)).tag;
		entity.velocityY = ((FloatTag)var3.getNbt(1)).tag;
		entity.velocityZ = ((FloatTag)var3.getNbt(2)).tag;
		entity.yaw = ((FloatTag)var4.getNbt(0)).tag;
		entity.pitch = ((FloatTag)var4.getNbt(1)).tag;
		Accessors.getEntity(entity).setFallDistance(nbtCompound.m_0000382("FallDistance"));
		//entity.onFireTimer = nbtCompound.getShort("Fire");
		if (entity instanceof LivingEntity) ((LivingEntity) entity).f_0554371 = nbtCompound.getInt("Air");
		//entity.refreshPositionAndAngles(entity.x, entity.y, entity.z, entity.yaw, entity.pitch);
		entity.setPosition(entity.x, entity.y, entity.z);
		if (entity instanceof PlayerEntity) {
			TagList inventory = nbtCompound.getList("Inventory");
			PlayerInventory playerInventory = new PlayerInventory();
			for(int item = 0; item < inventory.index(); ++item) {
				TagCompound index = (TagCompound)inventory.getNbt(item);
				int count = index.getByte("Count");
				int slot = index.getByte("Slot");
				// We check for `itemId` as an older version of mclm_save accidentally saved items as `itemId`.
				// We check for `blockId` without the config option as it's only needed on save.
				Couple[] idTypes = new Couple[]{new Couple("id", false), new Couple("itemId", false), new Couple("blockId", true)};
				for (Couple type : idTypes) prepStack((String)type.getFirst(), playerInventory, count, slot, index, (boolean)type.getSecond());
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
		nbtCompound.addNbt("Fire", new ShortTag((short) -20));
		if (entity instanceof PlayerEntity) {
			nbtCompound.addNbt("id", new StringTag("LocalPlayer"));
			TagList inventory = new TagList();
			for(int invSlot = 0; invSlot < ((PlayerEntity)entity).inventory.inventorySlots.length; ++invSlot) {
				TagCompound inventorySlot = new TagCompound();
				ItemStack stack = ((PlayerEntity)entity).inventory.inventorySlots[invSlot];
				if (stack != null && (stack.itemId != -1 || (SaveConfig.instance.saveBlockItems.value() && stack.f_9064670 != -1))) {
					inventorySlot.addNbt("Count", new ByteTag((byte) stack.size));
					inventorySlot.addNbt("id", new ShortTag((short) stack.itemId));
					inventorySlot.addNbt("Slot", new ByteTag((byte) invSlot));
					if (SaveConfig.instance.saveBlockItems.value() && stack.f_9064670 != -1) inventorySlot.addNbt("blockId", new ShortTag((short) stack.f_9064670));
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
		if (stack != null && (stack.itemId != -1 || stack.f_9064670 != -1)) inventorySlots[slot] = stack;
	}
	public final void prepStack(String type, PlayerInventory playerInventory, int count, int slot, TagCompound index, boolean isBlock) {
		if (index.getElements().containsKey(type)) {
			int id = index.getShort(type);
			if (id != -1) {
				if (!isBlock) {
					ItemStack stack = new ItemStack(id);
					stack.size = count;
					setStack(playerInventory.inventorySlots, slot, stack);
				} else {
					if (Block.BY_ID[id] != null) setStack(playerInventory.inventorySlots, slot, new ItemStack(Block.BY_ID[id], count));
				}
			}
		}
	}
	// 110 doesn't have the isBlock tag, to allow for compatibility we assume false if not given in 104 and lower.
	public final void prepStack(String type, PlayerInventory playerInventory, int count, int slot, TagCompound index) {
		prepStack(type, playerInventory, count, slot, index, false);
	}
}
