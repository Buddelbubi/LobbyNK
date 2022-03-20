/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.entity.Entity
 */
package de.buddelbubi.implementedcode.network.scoreboard.network;

import cn.nukkit.entity.Entity;
import de.buddelbubi.implementedcode.network.scoreboard.network.DisplayEntry;
import de.buddelbubi.implementedcode.network.scoreboard.network.Scoreboard;
import de.buddelbubi.implementedcode.network.scoreboard.network.SortOrder;
import java.beans.ConstructorProperties;

public class ScoreboardDisplay {
    private final Scoreboard scoreboard;
    private final String objectiveName;
    private String displayName;
    private SortOrder sortOrder;

    public DisplayEntry addEntity(Entity entity, int score) {
        long scoreId = this.scoreboard.addOrUpdateEntity(entity, this.objectiveName, score);
        return new DisplayEntry(this.scoreboard, scoreId);
    }

    public DisplayEntry addLine(String line, int score) {
        long scoreId = this.scoreboard.addOrUpdateLine(line, this.objectiveName, score);
        return new DisplayEntry(this.scoreboard, scoreId);
    }

    public void removeEntry(DisplayEntry entry) {
        this.scoreboard.removeScoreEntry(entry.getScoreId());
    }

    @ConstructorProperties(value={"scoreboard", "objectiveName", "displayName", "sortOrder"})
    public ScoreboardDisplay(Scoreboard scoreboard, String objectiveName, String displayName, SortOrder sortOrder) {
        this.scoreboard = scoreboard;
        this.objectiveName = objectiveName;
        this.displayName = displayName;
        this.sortOrder = sortOrder;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScoreboardDisplay)) {
            return false;
        }
        ScoreboardDisplay other = (ScoreboardDisplay)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Scoreboard this$scoreboard = this.getScoreboard();
        Scoreboard other$scoreboard = other.getScoreboard();
        if (!(this$scoreboard == null ? other$scoreboard == null : this$scoreboard.equals(other$scoreboard))) {
            return false;
        }
        String this$objectiveName = this.getObjectiveName();
        String other$objectiveName = other.getObjectiveName();
        if (!(this$objectiveName == null ? other$objectiveName == null : this$objectiveName.equals(other$objectiveName))) {
            return false;
        }
        String this$displayName = this.getDisplayName();
        String other$displayName = other.getDisplayName();
        if (!(this$displayName == null ? other$displayName == null : this$displayName.equals(other$displayName))) {
            return false;
        }
        SortOrder this$sortOrder = this.getSortOrder();
        SortOrder other$sortOrder = other.getSortOrder();
        return this$sortOrder == null ? other$sortOrder == null : ((Object)((Object)this$sortOrder)).equals((Object)other$sortOrder);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ScoreboardDisplay;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Scoreboard $scoreboard = this.getScoreboard();
        result = result * 59 + ($scoreboard == null ? 43 : $scoreboard.hashCode());
        String $objectiveName = this.getObjectiveName();
        result = result * 59 + ($objectiveName == null ? 43 : $objectiveName.hashCode());
        String $displayName = this.getDisplayName();
        result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
        SortOrder $sortOrder = this.getSortOrder();
        result = result * 59 + ($sortOrder == null ? 43 : ((Object)((Object)$sortOrder)).hashCode());
        return result;
    }

    public String toString() {
        return "ScoreboardDisplay(scoreboard=" + this.getScoreboard() + ", objectiveName=" + this.getObjectiveName() + ", displayName=" + this.getDisplayName() + ", sortOrder=" + (Object)((Object)this.getSortOrder()) + ")";
    }
}

