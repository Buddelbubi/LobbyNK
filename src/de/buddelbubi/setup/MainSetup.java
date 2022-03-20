/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.form.element.ElementButton
 *  cn.nukkit.form.window.FormWindow
 *  cn.nukkit.form.window.FormWindowSimple
 */
package de.buddelbubi.setup;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import de.buddelbubi.setup.SetupEventManager;

public class MainSetup {
    public static void startSetup(Player p) {
        FormWindowSimple fw = new FormWindowSimple("\u00a7cWelcome to the Setup of LobbyNK", "\u00a77Lets get started. Which configuration do you want to change?");
        fw.addButton(new ElementButton("Config.yml"));
        p.showFormWindow((FormWindow)fw);
        SetupEventManager.isp = true;
    }
}

