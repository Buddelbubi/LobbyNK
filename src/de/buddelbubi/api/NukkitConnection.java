/*
 * Decompiled with CFR 0.150.
 */
package de.buddelbubi.api;

import java.net.URL;

public class NukkitConnection {
    String address;
    Integer port;

    public NukkitConnection() {
    }

    public NukkitConnection(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getOnlinePlayers() {
        return -1;
    }

    public int getMaxPlayers() {
        return -1;
    }

    public String getMotd() {
        return "Outdated";
    }

    public boolean canConnect() {
        try {
            URL url = new URL("http://" + this.address + ":" + this.port);
            url.openStream();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

