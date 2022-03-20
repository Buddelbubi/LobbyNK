/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.network.protocol.DataPacket
 */
package de.buddelbubi.implementedcode.network.scoreboard.network.packet;

import cn.nukkit.network.protocol.DataPacket;
import java.beans.ConstructorProperties;
import java.util.List;

public class SetScorePacket
extends DataPacket {
    public static final byte NETWORK_ID = 108;
    private byte type;
    private List<ScoreEntry> entries;

    public byte pid() {
        return 108;
    }

    public void decode() {
    }

    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putUnsignedVarInt(this.entries.size());
        for (ScoreEntry entry : this.entries) {
            this.putVarLong(entry.scoreId);
            this.putString(entry.objective);
            this.putLInt(entry.score);
            if (this.type != 0) continue;
            this.putByte(entry.entityType);
            switch (entry.entityType) {
                case 3: {
                    this.putString(entry.fakeEntity);
                    break;
                }
                case 1: 
                case 2: {
                    this.putUnsignedVarLong(entry.entityId);
                }
            }
        }
    }

    public byte getType() {
        return this.type;
    }

    public List<ScoreEntry> getEntries() {
        return this.entries;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setEntries(List<ScoreEntry> entries) {
        this.entries = entries;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SetScorePacket)) {
            return false;
        }
        SetScorePacket other = (SetScorePacket)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        if (this.getType() != other.getType()) {
            return false;
        }
        List<ScoreEntry> this$entries = this.getEntries();
        List<ScoreEntry> other$entries = other.getEntries();
        return this$entries == null ? other$entries == null : ((Object)this$entries).equals(other$entries);
    }

    protected boolean canEqual(Object other) {
        return other instanceof SetScorePacket;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getType();
        List<ScoreEntry> $entries = this.getEntries();
        result = result * 59 + ($entries == null ? 43 : ((Object)$entries).hashCode());
        return result;
    }

    public String toString() {
        return "SetScorePacket(type=" + this.getType() + ", entries=" + this.getEntries() + ")";
    }

    public static class ScoreEntry {
        private final long scoreId;
        private final String objective;
        private final int score;
        private byte entityType;
        private String fakeEntity;
        private long entityId;

        public long getScoreId() {
            return this.scoreId;
        }

        public String getObjective() {
            return this.objective;
        }

        public int getScore() {
            return this.score;
        }

        public byte getEntityType() {
            return this.entityType;
        }

        public String getFakeEntity() {
            return this.fakeEntity;
        }

        public long getEntityId() {
            return this.entityId;
        }

        public String toString() {
            return "SetScorePacket.ScoreEntry(scoreId=" + this.getScoreId() + ", objective=" + this.getObjective() + ", score=" + this.getScore() + ", entityType=" + this.getEntityType() + ", fakeEntity=" + this.getFakeEntity() + ", entityId=" + this.getEntityId() + ")";
        }

        @ConstructorProperties(value={"scoreId", "objective", "score", "entityType", "fakeEntity", "entityId"})
        public ScoreEntry(long scoreId, String objective, int score, byte entityType, String fakeEntity, long entityId) {
            this.scoreId = scoreId;
            this.objective = objective;
            this.score = score;
            this.entityType = entityType;
            this.fakeEntity = fakeEntity;
            this.entityId = entityId;
        }

        @ConstructorProperties(value={"scoreId", "objective", "score"})
        public ScoreEntry(long scoreId, String objective, int score) {
            this.scoreId = scoreId;
            this.objective = objective;
            this.score = score;
        }
    }
}

