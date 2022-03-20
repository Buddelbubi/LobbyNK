/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.entity.item.EntityFirework
 *  cn.nukkit.item.Item
 *  cn.nukkit.item.ItemFirework
 *  cn.nukkit.item.ItemFirework$FireworkExplosion$ExplosionType
 *  cn.nukkit.level.Level
 *  cn.nukkit.level.format.FullChunk
 *  cn.nukkit.nbt.NBTIO
 *  cn.nukkit.nbt.tag.CompoundTag
 *  cn.nukkit.nbt.tag.DoubleTag
 *  cn.nukkit.nbt.tag.FloatTag
 *  cn.nukkit.nbt.tag.ListTag
 *  cn.nukkit.nbt.tag.Tag
 *  cn.nukkit.utils.DyeColor
 */
package de.buddelbubi.api;

import cn.nukkit.Player;
import cn.nukkit.entity.item.EntityFirework;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.DyeColor;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;
import java.util.Random;

public class spawnFirework {
    public static void spawnFirework(Player p) {
        ConfigNK c = Lobbynk.config;
        if (c.getBoolean("fireworkonspawn")) {
            double x = p.getX();
            double y = p.getY();
            double z = p.getZ();
            ItemFirework item = new ItemFirework();
            CompoundTag tag = new CompoundTag();
            Random random = new Random();
            CompoundTag ex = new CompoundTag().putByteArray("FireworkColor", new byte[]{(byte)DyeColor.values()[random.nextInt(ItemFirework.FireworkExplosion.ExplosionType.values().length)].getDyeData()}).putByteArray("FireworkFade", new byte[0]).putBoolean("FireworkFlicker", random.nextBoolean()).putBoolean("FireworkTrail", random.nextBoolean()).putByte("FireworkType", ItemFirework.FireworkExplosion.ExplosionType.values()[random.nextInt(ItemFirework.FireworkExplosion.ExplosionType.values().length)].ordinal());
            tag.putCompound("Fireworks", new CompoundTag("Fireworks").putList(new ListTag("Explosions").add((Tag)ex)).putByte("Flight", 1));
            item.setNamedTag(tag);
            CompoundTag nbt = new CompoundTag().putList(new ListTag("Pos").add((Tag)new DoubleTag("", x + 0.5)).add((Tag)new DoubleTag("", y + 0.5)).add((Tag)new DoubleTag("", z + 0.5))).putList(new ListTag("Motion").add((Tag)new DoubleTag("", 0.0)).add((Tag)new DoubleTag("", 0.0)).add((Tag)new DoubleTag("", 0.0))).putList(new ListTag("Rotation").add((Tag)new FloatTag("", 0.0f)).add((Tag)new FloatTag("", 0.0f))).putCompound("FireworkItem", NBTIO.putItemHelper((Item)item));
            Level l = p.getLevel();
            EntityFirework entity = new EntityFirework((FullChunk)l.getChunk((int)x >> 4, (int)z >> 4), nbt);
            entity.spawnToAll();
        }
    }
}

