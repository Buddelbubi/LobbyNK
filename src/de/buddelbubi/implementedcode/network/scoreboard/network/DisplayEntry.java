/*
 * Decompiled with CFR 0.150.
 */
package de.buddelbubi.implementedcode.network.scoreboard.network;

import de.buddelbubi.implementedcode.network.scoreboard.network.Scoreboard;
import java.beans.ConstructorProperties;

public class DisplayEntry {
    private final Scoreboard scoreboard;
    private final long scoreId;

    public void setScore(int score) {
        this.scoreboard.updateScore(this.scoreId, score);
    }

    public int getScore() {
        return this.scoreboard.getScore(this.scoreId);
    }

    @ConstructorProperties(value={"scoreboard", "scoreId"})
    public DisplayEntry(Scoreboard scoreboard, long scoreId) {
        this.scoreboard = scoreboard;
        this.scoreId = scoreId;
    }

    public long getScoreId() {
        return this.scoreId;
    }
}

