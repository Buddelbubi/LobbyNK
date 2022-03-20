/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 */
package de.buddelbubi.api;

import cn.nukkit.Player;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.ScoreboardAPI;
import de.buddelbubi.implementedcode.network.scoreboard.network.DisplaySlot;
import de.buddelbubi.implementedcode.network.scoreboard.network.Scoreboard;
import de.buddelbubi.implementedcode.network.scoreboard.network.ScoreboardDisplay;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {
    public static Map<String, Scoreboard> scoreboards = new HashMap<String, Scoreboard>();

    public static void createScoreboard(Player p) {
        Scoreboard sb = ScoreboardAPI.createScoreboard();
        ScoreboardDisplay sbd = sb.addDisplay(DisplaySlot.SIDEBAR, "dumy", Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Scoreboard.Header"));
        for (int i = 1; i <= 10; ++i) {
            sbd.addLine(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Scoreboard.Line" + i).replace("%player", p.getName()), i);
        }
        scoreboards.put(p.getName(), sb);
    }

    public static void setScoreboard(Player p) {
        if (scoreboards.containsKey(p.getName())) {
            scoreboards.get(p.getName()).showFor(p);
        } else {
            ScoreboardManager.createScoreboard(p);
            scoreboards.get(p.getName()).showFor(p);
        }
    }

    public static void hideScoreboard(Player p) {
        scoreboards.get(p.getName()).hideFor(p);
    }

    public static void deleteScoreboard(Player p) {
        try {
            scoreboards.get(p.getName()).hideFor(p);
        }
        catch (Exception exception) {
            // empty catch block
        }
        scoreboards.remove(p.getName());
    }
}

