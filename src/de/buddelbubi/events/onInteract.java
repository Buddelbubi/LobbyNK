
package de.buddelbubi.events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;
import de.buddelbubi.api.MultiLobbyManager;
import de.buddelbubi.api.NukkitConnection;
import de.buddelbubi.events.PetEvents;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;

public class onInteract
implements Listener {
    public static HashSet<String> ls = new HashSet();
    public static boolean inter = false;

    @EventHandler
    public void on(PlayerInteractEvent e) {
        ConfigNK c;
        block81: {
            Player p = e.getPlayer();
            c = Lobbynk.config;
            ConfigNK d = Lobbynk.delaydata;
            ConfigNK i = Lobbynk.images;
            ConfigNK l = Lobbynk.lang;
            ConfigNK h = Lobbynk.hotbar;
            ConfigNK m = Lobbynk.saves;
            String[] compass = h.getString("CompassItem").split(":");
            String[] gadgets = h.getString("GadgetsItem").split(":");
            String[] hider = h.getString("HidePlayerItem").split(":");
            String[] lobby = h.getString("LobbyItem").split(":");
            String[] settings = h.getString("SettingsItem").split(":");
            String[] forcefield = h.getString("ForcefieldItem").split(":");
            String[] silentlobby = h.getString("SilentlobbyItem").split(":");
            String[] frienditem = h.getString("FriendItem").split(":");
            if (!c.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
                if (m.getAll().containsKey(String.valueOf(p.getName()) + ".build") && m.get(String.valueOf(p.getName()) + ".build") != null) {
                    return;
                }
                if (c.getBoolean("spawnprotection")) {
                    e.setCancelled(true);
                }
                if (!d.getAll().containsKey(p.getName())) {
                    d.set(p.getName(), false);
                }
                if (!d.getBoolean(p.getName())) {
                    FormWindowSimple gui;
                    d.set(p.getName(), true);
                    d.save();
                    Server.getInstance().getScheduler().scheduleDelayedTask(Server.getInstance().getPluginManager().getPlugin("LobbyNK"), new Runnable(){

                        @Override
                        public void run() {
                            d.set(p.getName(), false);
                            d.save();
                        }
                    }, 10);
                    if (p.getInventory().getItemInHand().getId() == Integer.parseInt(compass[0]) && e.getAction() != PlayerInteractEvent.Action.PHYSICAL) {
                        e.setCancelled(true);
                        inter = true;
                        if (c.getBoolean("soundeffects")) {
                            e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.BLOCK_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f, Collections.singletonList(e.getPlayer()));
                        }
                        gui = new FormWindowSimple(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.name"), l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.subtitle"));
                        if (!l.has(String.valueOf(p.getPlayer().getLocale().toString()) + "_" + "Compass.Slot" + c.getInt("Gamesamount"))) {
                            e.getPlayer().sendMessage("\u00a7cYou didn't setup this many slots yet. Do /lobby gen [amount]");
                            return;
                        }
                        for (int dds = 1; dds != c.getInt("Gamesamount") + 1; ++dds) {
                            String compassname = l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.Slot" + dds).replace("%n", "\n");
                            if (l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.Slot" + dds).contains("%playercounter[")) {
                                int index = 1;
                                for (int in = 1; in < l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.Slot" + dds).split("\\s+").length; ++in) {
                                    if (!l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.Slot" + dds).split("\\s+")[in].startsWith("%playercounter")) continue;
                                    index = in;
                                }
                                String addressstring = l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Compass.Slot" + dds).split("\\s+")[index].replace("%playercounter[", "").replace("]", "");
                                String[] address = addressstring.split(":");
                                if (addressstring.contains("\ua789")) {
                                    address = addressstring.split("\ua789");
                                }
                                NukkitConnection con = address.length == 2 ? new NukkitConnection(address[0], Integer.parseInt(address[1])) : new NukkitConnection(address[0], 19132);
                                compassname = con.canConnect() ? compassname.replace("%playercounter[", "(" + con.getOnlinePlayers() + "/" + con.getMaxPlayers() + ")").replace(addressstring, "") : compassname.replace("%playercounter[", "\u00a7cOffline").replace(addressstring, "");
                            }
                            if (c.getBoolean("CompassImages")) {
                                ElementButtonImageData image = new ElementButtonImageData(i.getString("Slot" + dds + ".mode"), i.getString("Slot" + dds));
                                gui.addButton(new ElementButton(compassname, image));
                                continue;
                            }
                            gui.addButton(new ElementButton(compassname));
                        }
                        p.showFormWindow((FormWindow)gui);
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(hider[0]) && e.getAction() != PlayerInteractEvent.Action.PHYSICAL) {
                        e.setCancelled(true);
                        FormWindowSimple gui1 = new FormWindowSimple(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "playershider.name"), "");
                        ElementButtonImageData hideall = new ElementButtonImageData("path", "textures/items/dye_powder_lime.png");
                        ElementButtonImageData hidevip = new ElementButtonImageData("path", "textures/items/dye_powder_purple.png");
                        ElementButtonImageData hidenoone = new ElementButtonImageData("path", "textures/items/dye_powder_red.png");
                        gui1.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "playershider.hideall"), hideall));
                        gui1.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "playershider.hidevip"), hidevip));
                        gui1.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "playershider.hidenoone"), hidenoone));
                        p.showFormWindow((FormWindow)gui1);
                        if (c.getBoolean("soundeffects")) {
                            e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.BLOCK_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f, Collections.singletonList(e.getPlayer()));
                        }
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(gadgets[0]) && e.getAction() != PlayerInteractEvent.Action.PHYSICAL) {
                        ElementButtonImageData effects;
                        e.setCancelled(true);
                        if (c.getBoolean("soundeffects")) {
                            e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.RANDOM_CHESTOPEN, 1.0f, 1.0f, Collections.singletonList(e.getPlayer()));
                        }
                        gui = new FormWindowSimple(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.name"), l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.subtitle"));
                        if (e.getPlayer().hasPermission("lobbynk.gadgets.walkingparticles")) {
                            effects = new ElementButtonImageData("path", "textures/gui/newgui/Bundle/PaintBrush.png");
                            gui.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Walkingparticles"), effects));
                        }
                        if (e.getPlayer().hasPermission("lobbynk.gadgets.cosmetics")) {
                            ElementButtonImageData cmts = new ElementButtonImageData("path", "textures/ui/icon_armor.png");
                            gui.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Cosmetics"), cmts));
                        }
                        if (p.hasPermission("lobbynk.gadgets.effects")) {
                            ElementButtonImageData effe = new ElementButtonImageData("path", "textures/items/potion_bottle_heal.png");
                            gui.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.effects"), effe));
                        }
                        if (!(p.hasPermission("lobbynk.gadgets.walkingparticles") && p.hasPermission("lobbynk.gadgets.cosmetics") && p.hasPermission("lobbynk.gadgets.effects"))) {
                            gui.setContent(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "nopermission"));
                        }
                        if (e.getPlayer().hasPermission("lobbynk.gadgets.pets")) {
                            effects = new ElementButtonImageData("path", "textures/items/egg_npc.png");
                            gui.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pets"), effects));
                        }
                        if (e.getPlayer().hasPermission("lobbynk.remove")) {
                            gui.addButton(new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.remove")));
                        }
                        p.showFormWindow((FormWindow)gui);
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(lobby[0]) && e.getAction() != PlayerInteractEvent.Action.PHYSICAL) {
                        if (!c.getBoolean("multilobby")) {
                            e.getPlayer().sendMessage("\u00a7cMultilobby isn't activated! I would rather remove the LobbySelector from your hotbar.yml");
                            return;
                        }
                        gui = new FormWindowSimple(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector"), "");
                        for (String s : MultiLobbyManager.lobbyworlds) {
                            ElementButton eb;
                            Boolean cl = false;
                            if (e.getPlayer().getLevel().getName().equals(s)) {
                                cl = true;
                            }
                            if (s.startsWith("VIP-")) {
                                if (!p.hasPermission("lobbynk.lobby" + s.replace("VIP-", ""))) continue;
                                eb = new ElementButton((String.valueOf(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector.vipprefix")) + s.replace("VIP-", "") + " " + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Lobbyselector.Playercounter")).replace("%inlobby", String.valueOf(Server.getInstance().getLevelByName(s).getPlayers().size())).replace("%maxlobby", String.valueOf(c.getInt("maxplayersperlobby"))));
                                if (cl.booleanValue()) {
                                    eb.addImage(new ElementButtonImageData("path", "textures/persona_thumbnails/alex_hair_thumbnail_0.png"));
                                } else if (Server.getInstance().getLevelByName(s).getPlayers().size() != c.getInt("maxplayersperlobby")) {
                                    eb.addImage(new ElementButtonImageData("path", "textures/items/dye_powder_lime.png"));
                                } else {
                                    eb.addImage(new ElementButtonImageData("path", "textures/items/dye_powder_red.png"));
                                }
                                gui.addButton(eb);
                                continue;
                            }
                            eb = new ElementButton((String.valueOf(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector.prefix")) + s + " " + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Lobbyselector.Playercounter")).replace("%inlobby", String.valueOf(Server.getInstance().getLevelByName(s).getPlayers().size())).replace("%maxlobby", String.valueOf(c.getInt("maxplayersperlobby"))));
                            if (cl.booleanValue()) {
                                eb.addImage(new ElementButtonImageData("path", i.getString("currentlobby")));
                            } else if (Server.getInstance().getLevelByName(s).getPlayers().size() != c.getInt("maxplayersperlobby")) {
                                eb.addImage(new ElementButtonImageData("path", i.getString("nonfulllobby")));
                            } else {
                                eb.addImage(new ElementButtonImageData("path", i.getString("fulllobby")));
                            }
                            gui.addButton(eb);
                        }
                        for (String s : MultiLobbyManager.lobbyservers.keySet()) {
                            Config loc = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "locations.yml"));
                            String[] address = loc.getString("server." + s).split(":");
                            if (s.contains("onlineplayers") || loc.getString(s).contains("maxplayers")) {
                                ElementButton eb;
                                NukkitConnection con = new NukkitConnection(address[0], Integer.parseInt(address[1]));
                                if (con.canConnect()) {
                                    eb = new ElementButton(String.valueOf(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector.serverprefix")) + s.replace("server.", "").replace("onlineplayers", String.valueOf(con.getOnlinePlayers())).replace("maxplayers", String.valueOf(con.getMaxPlayers())), new ElementButtonImageData("path", i.getString("serverpicture")));
                                    gui.addButton(eb);
                                    continue;
                                }
                                eb = new ElementButton(String.valueOf(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector.serverprefix")) + s.replace("server.", "").replace("onlineplayers", "\u00a7cOffline\u00a7a").replace("maxplayers", "").replace("/", ""), new ElementButtonImageData("path", i.getString("serverpicture")));
                                gui.addButton(eb);
                                continue;
                            }
                            ElementButton eb = new ElementButton(String.valueOf(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyselector.serverprefix")) + s.replace("server.", ""), new ElementButtonImageData("path", i.getString("serverpicture")));
                            gui.addButton(eb);
                        }
                        p.showFormWindow((FormWindow)gui);
                        ls.add(e.getPlayer().getName());
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(settings[0]) && e.getAction() != PlayerInteractEvent.Action.PHYSICAL) {
                        ElementButton ff2;
                        ElementButton jumppad;
                        ElementButton fly;
                        ElementButton dj;
                        e.setCancelled(true);
                        if (!Lobbynk.playersettings.has(String.valueOf(e.getPlayer().getName()) + ".doublejump")) {
                            Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".doublejump", true);
                            Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".fly", false);
                            Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".jumppad", true);
                            Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".forcefield", false);
                        }
                        gui = new FormWindowSimple(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings"), "");
                        if (Boolean.valueOf(Lobbynk.playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".doublejump")).booleanValue() && p.hasPermission("lobbynk.doublejump")) {
                            dj = new ElementButton("\u00a7a" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.doublejump"));
                            dj.addImage(new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/jump_boost_effect.png"));
                            gui.addButton(dj);
                        } else if (p.hasPermission("lobbynk.doublejump")) {
                            dj = new ElementButton("\u00a7c" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.doublejump"));
                            dj.addImage(new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/jump_boost_effect.png"));
                            gui.addButton(dj);
                        }
                        if (Boolean.valueOf(Lobbynk.playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".fly")).booleanValue() && e.getPlayer().hasPermission("lobbynk.fly")) {
                            fly = new ElementButton("\u00a7a" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.fly"));
                            fly.addImage(new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/levitation_effect.png"));
                            gui.addButton(fly);
                        } else if (p.hasPermission("lobbynk.fly")) {
                            fly = new ElementButton("\u00a7c" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.fly"));
                            fly.addImage(new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/levitation_effect.png"));
                            gui.addButton(fly);
                        }
                        if (Boolean.valueOf(Lobbynk.playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".jumppad")).booleanValue()) {
                            jumppad = new ElementButton("\u00a7a" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.jumppad"));
                            jumppad.addImage(new ElementButtonImageData("path", "textures/items/emerald.png"));
                            gui.addButton(jumppad);
                        } else {
                            jumppad = new ElementButton("\u00a7c" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.jumppad"));
                            jumppad.addImage(new ElementButtonImageData("path", "textures/items/emerald.png"));
                            gui.addButton(jumppad);
                        }
                        if (Boolean.valueOf(Lobbynk.playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".forcefield")).booleanValue() && e.getPlayer().hasPermission("lobbynk.forcefield")) {
                            ff2 = new ElementButton("\u00a7a" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.forcefield"));
                            ff2.addImage(new ElementButtonImageData("path", "textures/items/ender_eye.png"));
                            gui.addButton(ff2);
                        } else if (e.getPlayer().hasPermission("lobbynk.forcefield")) {
                            ff2 = new ElementButton("\u00a7c" + l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.forcefield"));
                            ff2.addImage(new ElementButtonImageData("path", "textures/items/ender_eye.png"));
                            gui.addButton(ff2);
                        }
                        try {
                            if (Server.getInstance().getPluginManager().getPlugin("FriendSystem").isEnabled()) {
                                ff2 = new ElementButton("\u00a7eFriends");
                                ff2.addImage(new ElementButtonImageData("path", "textures/ui/multiplayer_glyph_color.png"));
                                gui.addButton(ff2);
                            }
                        }
                        catch (Exception ff21) {
                            // empty catch block
                        }
                        if (e.getPlayer().hasPermission("lobbynk.pet.settings") && PetEvents.map.containsKey(p.getName())) {
                            ElementButton pet = new ElementButton(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Petsettings"));
                            pet.addImage(new ElementButtonImageData("path", "textures/items/egg_npc.png"));
                            gui.addButton(pet);
                        }
                        p.showFormWindow((FormWindow)gui);
                    }
                    if (p.getInventory().getItemInHand().getId() == 160) {
                        e.setCancelled(true);
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(forcefield[0])) {
                        e.setCancelled(true);
                        if (p.hasPermission("lobbynk.forcefield")) {
                            if (Lobbynk.playersettings.has(String.valueOf(p.getName()) + ".forcefield") && Boolean.valueOf(Lobbynk.playersettings.get(String.valueOf(p.getName()) + ".forcefield").toString()).booleanValue()) {
                                e.getPlayer().sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.disabled").replace("%mode", l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.forcefield")));
                                Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".forcefield", false);
                            } else {
                                e.getPlayer().sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.enabled").replace("%mode", l.getString(String.valueOf(p.getLocale().toString()) + "_" + "settings.forcefield")));
                                Lobbynk.playersettings.put(String.valueOf(e.getPlayer().getName()) + ".forcefield", true);
                            }
                        } else {
                            p.sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "nopermission"));
                        }
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(silentlobby[0])) {
                        e.setCancelled(true);
                        int lobbys = 0;
                        if (!c.getBoolean("multilobby")) {
                            e.getPlayer().sendMessage("\u00a7cMultilobby isn't activated! I would rather remove the SilentLobbyItem from your hotbar.yml");
                            return;
                        }
                        for (String s : MultiLobbyManager.lobbyworlds) {
                            if (!s.startsWith("VIP-") || !p.hasPermission("lobbynk.VIP." + s.replace("VIP-", "")) || Server.getInstance().getLevelByName(s).getPlayers().size() >= c.getInt("maxplayersperlobby") && !p.hasPermission("lobbynk.fulllobby")) continue;
                            ++lobbys;
                            Config loc = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "locations.yml"));
                            if (!loc.exists("spawn.x." + s)) continue;
                            double x = loc.getDouble("spawn.x." + s);
                            double y = loc.getDouble("spawn.y." + s);
                            double z = loc.getDouble("spawn.z." + s);
                            float yaw = (float)loc.getDouble("spawn.yaw." + s);
                            float pitch = (float)loc.getDouble("spawn.pitch." + s);
                            Level le = Server.getInstance().getLevelByName(loc.getString("spawn.world." + s));
                            Location tp = new Location(x, y, z, (double)yaw, (double)pitch, le);
                            e.getPlayer().teleport(tp);
                            e.getPlayer().sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "lobbyconnected").replace("%lobby", s.replace("VIP-", "")));
                            break;
                        }
                        if (lobbys == 0) {
                            p.sendMessage(l.getString(String.valueOf(p.getLocale().toString()) + "_" + "everylobbyisfull"));
                        }
                    } else if (p.getInventory().getItemInHand().getId() == Integer.parseInt(frienditem[0])) {
                        try {
                            if (Server.getInstance().getPluginManager().getPlugin("FriendSystem").isEnabled()) {
                                Server.getInstance().dispatchCommand((CommandSender)p, new Config(new File(Server.getInstance().getPluginManager().getPlugin("FriendSystem").getDataFolder(), "config.yml")).getString("Commands.Friend.Name"));
                                break block81;
                            }
                            p.sendMessage("\u00a7cThis Item requies the Plugin FriendSystem by Mundschutziii.");
                        }
                        catch (Exception e2) {
                            p.sendMessage("\u00a7cThis Item requies the Plugin FriendSystem by Mundschutziii.");
                        }
                    } else if (c.getBoolean("disableInteract") && !p.isCreative()) {
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(false);
                    }
                }
            }
        }
        if (!(!c.getBoolean("disableInteract") || e.getAction() != PlayerInteractEvent.Action.PHYSICAL || c.getBoolean("multiworld") && !MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName()) || e.getBlock().getId() != 60 && e.getBlock().getId() != 59)) {
            e.setCancelled(true);
            Block b = e.getBlock();
            b.getLevel().setBlock(new Vector3(b.getX(), b.getY(), b.getZ()), Block.get((int)b.getId()));
        }
    }
}

