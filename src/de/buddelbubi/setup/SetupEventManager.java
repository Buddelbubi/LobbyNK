/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.event.EventHandler
 *  cn.nukkit.event.Listener
 *  cn.nukkit.event.player.PlayerFormRespondedEvent
 *  cn.nukkit.form.element.Element
 *  cn.nukkit.form.element.ElementInput
 *  cn.nukkit.form.element.ElementSlider
 *  cn.nukkit.form.element.ElementToggle
 *  cn.nukkit.form.window.FormWindow
 *  cn.nukkit.form.window.FormWindowCustom
 *  cn.nukkit.form.window.FormWindowSimple
 *  cn.nukkit.utils.Config
 */
package de.buddelbubi.setup;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import java.io.File;

public class SetupEventManager
implements Listener {
    public static int step = 0;
    public static boolean isp = false;
    public static String sstring = "";
    public static int windowid = 0;
    Config c = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "config.yml"));

    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent e) {
        try {
            Player p = e.getPlayer();
            if (e.getWindow() instanceof FormWindowSimple) {
                sstring = "config";
                FormWindowCustom fw = new FormWindowCustom("\u00a7cConfig.yml\u00a77Edit your main Config");
                for (String s : this.c.getKeys()) {
                    if (s.equalsIgnoreCase("Configversion")) continue;
                    if (this.c.isBoolean(s)) {
                        fw.addElement((Element)new ElementToggle("\u00a7e" + s, this.c.getBoolean(s)));
                        continue;
                    }
                    if (this.c.isInt(s)) {
                        if (this.c.getInt(s) <= 10) {
                            fw.addElement((Element)new ElementSlider("\u00a7e" + s, 0.0f, 10.0f, 1, (float)this.c.getInt(s)));
                            continue;
                        }
                        if (this.c.getInt(s) <= 100) {
                            fw.addElement((Element)new ElementSlider("\u00a7e" + s, 0.0f, 100.0f, 1, (float)this.c.getInt(s)));
                            continue;
                        }
                        if (this.c.getInt(s) <= 10000) {
                            fw.addElement((Element)new ElementSlider("\u00a7e" + s, 0.0f, 10000.0f, 10, (float)this.c.getInt(s)));
                            continue;
                        }
                        fw.addElement((Element)new ElementInput("\u00a7e" + s, String.valueOf(this.c.getInt(s))));
                        continue;
                    }
                    fw.addElement((Element)new ElementInput("\u00a7e" + s, this.c.getString(s)));
                }
                p.showFormWindow((FormWindow)fw);
            } else {
                FormWindowCustom f = (FormWindowCustom)e.getWindow();
                for (int i = 0; i <= f.getResponse().getResponses().size(); ++i) {
                    Object v = f.getResponse().getResponses().get(i);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

