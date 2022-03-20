/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.utils.Config
 *  org.iq80.leveldb.util.FileUtils
 */
package de.buddelbubi.api;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import de.buddelbubi.Lobbynk;
import java.io.File;
import org.iq80.leveldb.util.FileUtils;

public class SyncLobby {
    public static void sync(String data, String shouldbewritten) {
        Config loc = new Config(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "locations.yml"));
        for (int i = 0; i != Lobbynk.config.getInt("Gamesamount") + 1; ++i) {
            if (i == 0) {
                if (!loc.exists("spawn.x." + data)) continue;
                loc.set("spawn.x." + shouldbewritten, loc.get("spawn.x." + data));
                loc.set("spawn.y." + shouldbewritten, loc.get("spawn.y." + data));
                loc.set("spawn.z." + shouldbewritten, loc.get("spawn.z." + data));
                loc.set("spawn.yaw." + shouldbewritten, loc.get("spawn.yaw." + data));
                loc.set("spawn.pitch." + shouldbewritten, loc.get("spawn.pitch." + data));
                loc.set("spawn.world." + shouldbewritten, (Object)shouldbewritten);
                loc.save();
                continue;
            }
            if (!loc.exists("loc" + i + ".x." + data)) continue;
            loc.set("loc" + i + ".x." + shouldbewritten, loc.get("loc" + i + ".x." + data));
            loc.set("loc" + i + ".y." + shouldbewritten, loc.get("loc" + i + ".y." + data));
            loc.set("loc" + i + ".z." + shouldbewritten, loc.get("loc" + i + ".z." + data));
            loc.set("loc" + i + ".yaw." + shouldbewritten, loc.get("loc" + i + ".yaw." + data));
            loc.set("loc" + i + ".pitch." + shouldbewritten, loc.get("loc" + i + ".pitch." + data));
            loc.set("loc" + i + ".world." + shouldbewritten, (Object)shouldbewritten);
            loc.save();
        }
        if (!Server.getInstance().getDefaultLevel().getName().equals(shouldbewritten)) {
            Server.getInstance().getLevelByName(shouldbewritten).unload(true);
            FileUtils.copyDirectoryContents((File)new File(String.valueOf(Server.getInstance().getDataPath()) + "/worlds/" + data), (File)new File(String.valueOf(Server.getInstance().getDataPath()) + "/worlds/" + shouldbewritten));
            Server.getInstance().loadLevel(shouldbewritten);
            if (Lobbynk.config.getBoolean("timelock")) {
                Server.getInstance().getLevelByName(shouldbewritten).setTime(Lobbynk.config.getInt("time"));
                Server.getInstance().getLevelByName(shouldbewritten).stopTime();
            }
        } else {
            Server.getInstance().getLogger().alert("[LobbyNK] >> Your default world wont get overwritten!");
        }
    }
}

