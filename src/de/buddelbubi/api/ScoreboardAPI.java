/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 */
package de.buddelbubi.api;

import cn.nukkit.Player;
import de.buddelbubi.implementedcode.network.scoreboard.network.Scoreboard;

public class ScoreboardAPI {
    public static Scoreboard createScoreboard() {
        return new Scoreboard();
    }

    public static void setScoreboard(Player player2, Scoreboard scoreboard) {
        scoreboard.showFor(player2);
    }

    public static void removeScorebaord(Player player2, Scoreboard scoreboard) {
        scoreboard.hideFor(player2);
    }
}

