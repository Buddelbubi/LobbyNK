/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.command.Command
 *  cn.nukkit.command.CommandSender
 */
package de.buddelbubi.chatystem;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ConfigNK;

public class clearchatcommand
extends Command {
    public clearchatcommand(String name) {
        super(name);
    }

    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        ConfigNK l = Lobbynk.lang;
        if (arg0.hasPermission("lobbynk.clearchat")) {
            for (int i = 0; i <= 52; ++i) {
                Server.getInstance().broadcastMessage("");
            }
            Server.getInstance().broadcastMessage(l.getString("Chat.cleared").replace("%player", arg0.getName()));
        }
        return false;
    }
}

