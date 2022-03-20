/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.item.Item
 *  cn.nukkit.level.Level
 *  cn.nukkit.level.Location
 *  cn.nukkit.utils.Config
 *  cn.nukkit.utils.DummyBossBar
 */
package de.buddelbubi.api;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DummyBossBar;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;
import de.buddelbubi.api.MultiLobbyManager;
import de.buddelbubi.api.ScoreboardManager;
import java.io.File;

public class player
extends Config {
    public static void giveHotbar(Player p) {
        Item it;
        String[] id;
        int dda;
        ConfigNK m = Lobbynk.saves;
        ConfigNK l = Lobbynk.lang;
        p.setCheckMovement(false);
        m.set(String.valueOf(p.getName()) + ".build", null);
        ConfigNK c = Lobbynk.config;
        p.setGamemode(c.getInt("Gamemode"));
        p.setExperience(0, c.getInt("Level"));
        p.setMaxHealth(c.getInt("maxHealth") * 2);
        p.setHealth((float)(c.getInt("maxHealth") * 2));
        p.getFoodData().setFoodLevel(20);
        Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

            @Override
            public void run() {
                if (p.getMaxHealth() != c.getInt("maxHealth")) {
                    p.setMaxHealth(c.getInt("maxHealth") * 2);
                    p.setHealth((float)(c.getInt("maxHealth") * 2));
                }
            }
        }, 20);
        ConfigNK h = Lobbynk.hotbar;
        p.getInventory().clearAll();
        if (h.getInt("configversion") != 2) {
            p.sendMessage("\u00a7cFATAL ERROR! - Your hotbar.yml (LobbyNK) is outdated! Please generate a new one!");
            return;
        }
        if (!p.hasPermission("lobbynk.vip")) {
            for (dda = 1; dda != 10; ++dda) {
                id = h.getString("Slot" + dda + ".Item").split(":");
                it = Item.get((int)Integer.parseInt(id[0]));
                it.setCustomName(h.getString("Slot" + dda + ".Itemname")).setDamage(Integer.valueOf(Integer.parseInt(id[1])));
                p.getInventory().setItem(dda - 1, it);
            }
        } else {
            for (dda = 1; dda != 10; ++dda) {
                id = h.getString("Slot" + dda + ".VIP.Item").split(":");
                it = Item.get((int)Integer.parseInt(id[0]));
                it.setCustomName(h.getString("Slot" + dda + ".VIP.Itemname")).setDamage(Integer.valueOf(Integer.parseInt(id[1])));
                p.getInventory().setItem(dda - 1, it);
            }
        }
        p.getInventory().sendContents(p);
        p.getInventory().sendContents(p);
        for (DummyBossBar b : p.getDummyBossBars().values()) {
            b.destroy();
        }
        if (c.getBoolean("Bossbar")) {
            if (!Lobbynk.versionCheck().contains(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion()) && p.isOp()) {
                p.createBossBar("\u00a7cLobbyNK got updated! Please update it...", 1);
            } else {
                p.createBossBar(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Bossbar").replace("%p", p.getName()), c.getInt("bossbarpoints"));
            }
        }
    }

    public static void toSpawn(Player p) {
        if (Lobbynk.config.getBoolean("scoreboard")) {
            ScoreboardManager.setScoreboard(p);
        }
        ConfigNK l = Lobbynk.lang;
        Config loc = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "locations.yml"));
        String w = "";
        if (p.riding != null) {
            p.riding.passengers.clear();
        }
        if (Lobbynk.config.getBoolean("multilobby") && !MultiLobbyManager.lobbyworlds.isEmpty()) {
            w = "." + MultiLobbyManager.getLobby();
        }
        if (w.equalsIgnoreCase(".error")) {
            p.sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "everylobbyisfull"));
            return;
        }
        if (loc.exists("spawn.x" + w)) {
            double x = loc.getDouble("spawn.x" + w);
            double y = loc.getDouble("spawn.y" + w);
            double z = loc.getDouble("spawn.z" + w);
            float yaw = (float)loc.getDouble("spawn.yaw" + w);
            float pitch = (float)loc.getDouble("spawn.pitch" + w);
            Level le = Server.getInstance().getLevelByName(loc.getString("spawn.world" + w));
            Location tp = new Location(x, y, z, (double)yaw, (double)pitch, le);
            p.teleport(tp);
            p.sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "spawn.tp"));
        } else {
            p.sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Locations.Teleport.missing").replace("%game", "spawn"));
        }
    }
}

