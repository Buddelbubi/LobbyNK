/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.blockentity.BlockEntitySign
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.block.BlockPlaceEvent
 *  cn.nukkit.math.Vector3
 */
package de.buddelbubi.events;

import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.math.Vector3;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.MultiLobbyManager;
import java.util.HashMap;
import java.util.Map;

public class ServerSigns
implements Listener {
    public static Map<String, String[]> signs = new HashMap<String, String[]>();

    @EventHandler
    public void onSignPlaced(BlockPlaceEvent e) {
        if ((e.getBlock().getId() == 63 || e.getBlock().getId() == 68) && e.getPlayer().getLevel().getBlockEntity((Vector3)e.getBlock().getLocation()) instanceof BlockEntitySign) {
            BlockEntitySign sign = (BlockEntitySign)e.getPlayer().getLevel().getBlockEntity((Vector3)e.getBlock().getLocation());
            if (sign.getText()[0].equalsIgnoreCase("[lobbysign]") && e.getPlayer().hasPermission("lobbynk.createsign")) {
                if (sign.getText().length == 3) {
                    String[] args = sign.getText();
                    if (args[1].equalsIgnoreCase("lobby")) {
                        if (MultiLobbyManager.lobbyworlds.contains(args[2])) {
                            sign.setText(new String[]{Lobbynk.lang.getString("Sign.Prefix"), Lobbynk.lang.getString("Sign.Type").replace("%type", args[1]), Lobbynk.lang.getString("Sign.Name").replace("%name", args[2]), Lobbynk.lang.getString("Sign.Online").replace("%playercounter", String.valueOf(Server.getInstance().getLevelByName(args[2]).getPlayers().size()) + "/" + Lobbynk.config.getInt("maxplayersperlobby"))});
                            Lobbynk.loc.set("Sign.lobby" + sign.x + "=" + sign.y + "=" + sign.z, (Object)args[2]);
                            Lobbynk.loc.save();
                            signs.put(String.valueOf(sign.x) + "=" + sign.y + "=" + sign.z, args);
                        } else {
                            e.getPlayer().sendMessage("\u00a7cLobby " + args[2] + " not found");
                        }
                    } else {
                        args[1].equalsIgnoreCase("server");
                    }
                } else {
                    e.getPlayer().sendMessage("\u00a7cMissing arguments");
                }
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Lobbynk.lang.getString("nopermission"));
            }
        }
    }

    public static void rollbackSigns() {
        for (String s : signs.keySet()) {
            String[] arrstring = s.split("=");
        }
    }
}

