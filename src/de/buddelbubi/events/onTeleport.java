/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerTeleportEvent
 *  cn.nukkit.item.Item
 *  cn.nukkit.level.Level
 *  cn.nukkit.level.Sound
 *  cn.nukkit.math.Vector3
 *  cn.nukkit.utils.DummyBossBar
 */
package de.buddelbubi.events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.DummyBossBar;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;
import de.buddelbubi.api.MultiLobbyManager;
import de.buddelbubi.api.ScoreboardManager;
import de.buddelbubi.api.player;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class onTeleport
implements Listener {
    public static Map<String, Map<Integer, Item>> invs = new HashMap<String, Map<Integer, Item>>();
    public static Map<String, Integer> xp = new HashMap<String, Integer>();
    public static Map<String, Integer> level = new HashMap<String, Integer>();

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        ConfigNK c = Lobbynk.config;
        ConfigNK s = Lobbynk.saves;
        if (e.isCancelled()) {
            return;
        }
        if (c.getBoolean("multiworld")) {
            if (MultiLobbyManager.lobbyworlds.contains(e.getTo().getLevel().getName()) && !MultiLobbyManager.lobbyworlds.contains(e.getFrom().getLevel().getName())) {
                if (e.getPlayer().getGamemode() != 1) {
                    xp.put(e.getPlayer().getName(), e.getPlayer().getExperience());
                    level.put(e.getPlayer().getName(), e.getPlayer().getExperienceLevel());
                    invs.put(e.getPlayer().getName(), e.getPlayer().getInventory().getContents());
                }
                Level level = e.getTo().getLevel();
                Vector3 vec = new Vector3(e.getTo().getX(), e.getTo().getY(), e.getTo().getZ());
                if (c.getBoolean("soundeffects")) {
                    level.addSound(vec, Sound.MOB_SHULKER_TELEPORT, 1.0f, 1.0f, Collections.singletonList(e.getPlayer()));
                }
                if (c.getBoolean("scoreboard")) {
                    ScoreboardManager.setScoreboard(e.getPlayer());
                }
                player.giveHotbar(e.getPlayer());
            } else {
                if (s.getAll().containsKey(String.valueOf(e.getPlayer().getName()) + ".hidden") && s.getBoolean(String.valueOf(e.getPlayer().getName()) + ".hidden")) {
                    s.set(String.valueOf(e.getPlayer().getName()) + ".hidden", false);
                    for (Player o : Server.getInstance().getOnlinePlayers().values()) {
                        e.getPlayer().showPlayer(o);
                    }
                }
                if (c.getBoolean("multiworldSaveInventory") && MultiLobbyManager.lobbyworlds.contains(e.getFrom().getLevel().getName()) && !MultiLobbyManager.lobbyworlds.contains(e.getTo().getLevel().getName())) {
                    if (invs.containsKey(e.getPlayer().getName())) {
                        e.getPlayer().getInventory().clearAll();
                        e.getPlayer().getInventory().setContents(invs.get(e.getPlayer().getName()));
                        e.getPlayer().setExperience(xp.get(e.getPlayer().getName()).intValue(), level.get(e.getPlayer().getName()).intValue());
                        e.getPlayer().getInventory().sendContents(e.getPlayer());
                    } else {
                        e.getPlayer().getInventory().clearAll();
                    }
                }
                if (MultiLobbyManager.lobbyworlds.contains(e.getFrom().getLevel().getName()) && !MultiLobbyManager.lobbyworlds.contains(e.getTo().getLevel().getName())) {
                    e.getPlayer().setGamemode(Server.getInstance().getDefaultGamemode());
                    e.getPlayer().setMaxHealth(20);
                    e.getPlayer().setHealth(20.0f);
                    e.getPlayer().setAllowFlight(false);
                    e.getPlayer().removeAllEffects();
                    if (c.getBoolean("scoreboard")) {
                        ScoreboardManager.hideScoreboard(e.getPlayer());
                    }
                    for (DummyBossBar b : e.getPlayer().getDummyBossBars().values()) {
                        b.destroy();
                    }
                }
            }
        } else {
            Level level = e.getTo().getLevel();
            Vector3 vec = new Vector3(e.getTo().getX(), e.getTo().getY(), e.getTo().getZ());
            if (c.getBoolean("soundeffects") && e.getPlayer().spawned) {
                Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            level.addSound(vec, Sound.MOB_SHULKER_TELEPORT, 1.0f, 1.0f, Collections.singletonList(e.getPlayer()));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }, 5);
            }
        }
    }
}

