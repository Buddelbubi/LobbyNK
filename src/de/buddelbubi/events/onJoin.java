
package de.buddelbubi.events;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DummyBossBar;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;
import de.buddelbubi.api.MultiLobbyManager;
import de.buddelbubi.api.player;
import de.buddelbubi.api.spawnFirework;
import de.buddelbubi.events.onTeleport;
import java.io.File;

public class onJoin
implements Listener {
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onJoin(PlayerRespawnEvent e) {
        if (!e.isFirstSpawn()) {
            return;
        }
        Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){
         

            @Override
            public void run() {
                Location tp;
                Level le;
                float pitch;
                float yaw;
                double z;
                double y;
                double x;
                String world;
                Config loc;
                ConfigNK c;
                if (!e.isFirstSpawn()) {
                    return;
                }
                if (!Lobbynk.playersettings.has(String.valueOf(e.getPlayer().getName()) + ".jumppad")) {
                    Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".jumppad", true);
                }
                if ((c = Lobbynk.config).getBoolean("DeviceDisplay")) {
                    String device = "Unknown";
                    switch (e.getPlayer().getLoginChainData().getDeviceOS()) {
                        case 0: {
                            device = "Unknown";
                            break;
                        }
                        case 1: {
                            device = "Android";
                            break;
                        }
                        case 2: {
                            device = "iOS";
                            break;
                        }
                        case 3: {
                            device = "MacOS";
                            break;
                        }
                        case 4: {
                            device = "FireOS";
                            break;
                        }
                        case 5: {
                            device = "GearVR";
                            break;
                        }
                        case 6: {
                            device = "HoloLens";
                            break;
                        }
                        case 7: {
                            device = "Windows 10";
                            break;
                        }
                        case 8: {
                            device = "Windows";
                            break;
                        }
                        case 9: {
                            device = "Dedicated";
                            break;
                        }
                        case 10: {
                            device = "PlayStation 4";
                            break;
                        }
                        case 11: {
                            device = "Switch";
                            break;
                        }
                        default: {
                            device = "Unknown";
                        }
                    }
                    e.getPlayer().setNameTag(String.valueOf(e.getPlayer().getDisplayName()) + "\n\u00a77" + device);
                }
                final ConfigNK lang = Lobbynk.lang;
                if (!c.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
                    onTeleport.invs.put(e.getPlayer().getName(), e.getPlayer().getInventory().getContents());
                    onTeleport.xp.put(e.getPlayer().getName(), e.getPlayer().getExperience());
                    onTeleport.level.put(e.getPlayer().getName(), e.getPlayer().getExperienceLevel());
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

                        @Override
                        public void run() {
                            player.giveHotbar(e.getPlayer());
                            Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

                                @Override
                                public void run() {
                                    if (e.getPlayer().getGamemode() != 1) {
                                        for (DummyBossBar b : e.getPlayer().getDummyBossBars().values()) {
                                            b.destroy();
                                        }
                                        if (c.getBoolean("Bossbar")) {
                                            e.getPlayer().createBossBar(lang.getString(String.valueOf(e.getPlayer().getLocale().toString()) + "_" + "Bossbar").replace("%p", e.getPlayer().getName()), c.getInt("bossbarpoints"));
                                        }
                                        if (MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
                                            player.giveHotbar(e.getPlayer());
                                        }
                                    }
                                }
                            }, 20);
                        }
                    }, 2);
                }
                if (c.getBoolean("alwaysspawn") && !c.getBoolean("multilobby")) {
                    loc = new Config(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "locations.yml"));
                    world = "";
                    if (MultiLobbyManager.lobbyworlds.isEmpty() && c.getBoolean("multiworld")) {
                        Server.getInstance().getLogger().critical("\u00a74No lobbyworlds found. Please add a lobbyworld");
                        return;
                    }
                    if (c.getBoolean("multilobby")) {
                        world = "." + MultiLobbyManager.getLobby();
                    }
                    if (loc.getString("spawn.world" + world) != null) {
                        try {
                            x = loc.getDouble("spawn.x" + world);
                            y = loc.getDouble("spawn.y" + world);
                            z = loc.getDouble("spawn.z" + world);
                            yaw = (float)loc.getDouble("spawn.yaw" + world);
                            pitch = (float)loc.getDouble("spawn.pitch" + world);
                            le = Server.getInstance().getLevelByName(loc.getString("spawn.world" + world));
                            tp = new Location(x, y, z, (double)yaw, (double)pitch, le);
                            if (c.getBoolean("alwaysspawn")) {
                                e.setRespawnPosition((Position)tp);
                            }
                            if (c.getBoolean("fireworkonspawn")) {
                                spawnFirework.spawnFirework(e.getPlayer());
                            }
                        }
                        catch (Exception e2) {
                            System.out.print("[LobbyNK] >>>> Missing SpawnLocation for this world! Please do /lobby set spawn in world " + world);
                        }
                    } else {
                        System.out.println("LobbyNK | Error orrupted: No spawnlocation");
                        e.getPlayer().teleport(e.getPlayer().getLevel().getSpawnLocation());
                    }
                } else if (!c.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
                    spawnFirework.spawnFirework(e.getPlayer());
                }
                if (c.getBoolean("multilobby")) {
                    loc = new Config(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "locations.yml"));
                    world = "";
                    if (MultiLobbyManager.lobbyworlds.isEmpty() && c.getBoolean("multiworld")) {
                        Server.getInstance().getLogger().critical("\u00a74No lobbyworlds found. Please add a lobbyworld");
                        return;
                    }
                    if (c.getBoolean("multilobby")) {
                        world = "." + MultiLobbyManager.getLobby();
                    }
                    if (loc.getString("spawn.world" + world) != null) {
                        try {
                            x = loc.getDouble("spawn.x" + world);
                            y = loc.getDouble("spawn.y" + world);
                            z = loc.getDouble("spawn.z" + world);
                            yaw = (float)loc.getDouble("spawn.yaw" + world);
                            pitch = (float)loc.getDouble("spawn.pitch" + world);
                            le = Server.getInstance().getLevelByName(loc.getString("spawn.world" + world));
                            tp = new Location(x, y, z, (double)yaw, (double)pitch, le);
                            if (c.getBoolean("alwaysspawn")) {
                                e.setRespawnPosition((Position)tp);
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    Server.getInstance().getScheduler().scheduleDelayedTask(Server.getInstance().getPluginManager().getPlugin("LobbyNK"), new Runnable(){

                        @Override
                        public void run() {
                            if (c.getBoolean("alwaysspawn")) {
                                player.toSpawn(e.getPlayer());
                            }
                            spawnFirework.spawnFirework(e.getPlayer());
                        }
                    }, 30);
                }
            }
        }, 10);
    }
}

