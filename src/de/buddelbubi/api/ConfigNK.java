/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 */
package de.buddelbubi.api;

import cn.nukkit.Server;
import de.buddelbubi.api.MultiLobbyManager;
import java.util.HashMap;
import java.util.Map;

public class ConfigNK {
    HashMap<String, Object> data;

    public ConfigNK() {
        this.data = new HashMap();
    }

    public ConfigNK(Map<String, ?> map) {
        this.data = new HashMap(map);
        try {
            for (String key : this.data.keySet()) {
                this.data.put(key, this.data.get(key).toString().replace("&", "\u00a7"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Server.getInstance().getLogger().info("Please send this error code in the LobbyNK discussion tab. (Not reviews) - https://cloudburstmc.org/threads/lobbynk.623/\nDiscord would work as well: Buddelbubi#5018");
            Server.getInstance().getLogger().error("One of your LobbyNK Config Files is orrupted!!! Please reset your config files.");
            System.exit(1);
        }
    }

    public boolean has(String key) {
        return this.data.containsKey(key);
    }

    public int getInt(String key) {
        try {
            return Integer.valueOf((String)this.data.get(key));
        }
        catch (Exception e) {
            Server.getInstance().getLogger().warning("\u00a74Your option \u00a7c" + key + " \u00a74is orrupted or not an integer!");
            return 0;
        }
    }

    public Map<String, Object> getAll() {
        return this.data;
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public String getString(String key) {
        MultiLobbyManager.lobbyworlds.remove("");
        return String.valueOf(this.data.get(key));
    }

    public boolean getBoolean(String key) {
        block5: {
            block4: {
                try {
                    if (!this.data.get(key).toString().equals("true")) break block4;
                    return true;
                }
                catch (Exception e) {
                    Server.getInstance().getLogger().critical("\u00a74LobbyNK couldn't read a boolean. Send this to the developer!");
                    e.printStackTrace();
                    return false;
                }
            }
            if (!this.data.get(key).toString().equals("false")) break block5;
            return false;
        }
        System.out.print("Error while reading boolean: " + this.data.get(key));
        return false;
    }

    public void remove(String key) {
        this.data.remove(key);
    }

    public double getDouble(String key) {
        return Double.parseDouble(this.data.get(key).toString());
    }

    public void set(String key, Object obj) {
        this.data.put(key, obj);
    }

    public void save() {
    }

    public boolean exists(String string) {
        return this.data.containsKey(string);
    }

    public void put(String key, Object obj) {
        this.data.put(key, obj);
    }

	public String getAsString(String string) {
		return getString(string);
	}
}

