/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.level.Level
 *  cn.nukkit.utils.Config
 */
package de.buddelbubi.api;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import de.buddelbubi.Lobbynk;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiLobbyManager {
    public static List<String> lobbyworlds = new ArrayList<String>();
    public static Map<String, String> lobbyservers = new HashMap<String, String>();

    public static void registerAllLobbyworlds() {
        Config loc = new Config(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "locations.yml"));
        if (Lobbynk.config.getBoolean("multilobby-useallworlds")) {
            for (Level s : Server.getInstance().getLevels().values()) {
                lobbyworlds.add(s.getName());
            }
        } else {
            for (String s : loc.getKeys()) {
                if (!s.contains("spawn.world") || !s.contains(loc.getString(s))) continue;
                lobbyworlds.add(loc.getString(s));
            }
        }
        Collections.sort(lobbyworlds);
    }

    public static void registerAllLobbyservers() {
        Config loc = new Config(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "locations.yml"));
        for (String s : loc.getKeys()) {
            if (!s.startsWith("server.")) continue;
            if (!lobbyworlds.contains(s.replace("server.", ""))) {
                lobbyservers.put(s.replace("server.", ""), loc.getString(s));
                continue;
            }
            Server.getInstance().getLogger().alert("\u00a7cLobbyNK >>> Lobbyworld and Lobbyserver are called the same way. Lobbyworld overwrites server! \u00a7e(" + s.replace("server.", "") + ")");
        }
    }

    public static String getLobby() {
        if (lobbyworlds.size() == 0) {
            return "nosetup";
        }
        for (String s : lobbyworlds) {
            if (s.startsWith("VIP-")) continue;
            try {
                if (Server.getInstance().getLevelByName(s).getPlayers().size() >= Lobbynk.config.getInt("maxplayersperlobby")) continue;
                return s;
            }
            catch (Exception e) {
                Server.getInstance().getLogger().critical("\u00a74LOBBY WORLD DELETED!!! Please remove it from your locations.yml");
                lobbyworlds.remove(s);
            }
        }
        return "error";
    }
}

