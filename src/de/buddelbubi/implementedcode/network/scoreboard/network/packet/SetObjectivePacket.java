/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.network.protocol.DataPacket
 */
package de.buddelbubi.implementedcode.network.scoreboard.network.packet;

import cn.nukkit.network.protocol.DataPacket;

public class SetObjectivePacket
extends DataPacket {
    public static final byte NETWORK_ID = 107;
    public String displaySlot;
    public String objectiveName;
    public String displayName;
    public String criteriaName;
    public int sortOrder;

    public byte pid() {
        return 107;
    }

    public void decode() {
    }

    public void encode() {
        this.reset();
        this.putString(this.displaySlot);
        this.putString(this.objectiveName);
        this.putString(this.displayName);
        this.putString(this.criteriaName);
        this.putVarInt(this.sortOrder);
    }

    public String getDisplaySlot() {
        return this.displaySlot;
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getCriteriaName() {
        return this.criteriaName;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setDisplaySlot(String displaySlot) {
        this.displaySlot = displaySlot;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SetObjectivePacket)) {
            return false;
        }
        SetObjectivePacket other = (SetObjectivePacket)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        String this$displaySlot = this.getDisplaySlot();
        String other$displaySlot = other.getDisplaySlot();
        if (!(this$displaySlot == null ? other$displaySlot == null : this$displaySlot.equals(other$displaySlot))) {
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
        String this$criteriaName = this.getCriteriaName();
        String other$criteriaName = other.getCriteriaName();
        if (this$criteriaName == null) {
            if (other$criteriaName == null) {
                return this.getSortOrder() == other.getSortOrder();
            }
        } else if (this$criteriaName.equals(other$criteriaName)) {
            return this.getSortOrder() == other.getSortOrder();
        }
        return false;
    }

    protected boolean canEqual(Object other) {
        return other instanceof SetObjectivePacket;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $displaySlot = this.getDisplaySlot();
        result = result * 59 + ($displaySlot == null ? 43 : $displaySlot.hashCode());
        String $objectiveName = this.getObjectiveName();
        result = result * 59 + ($objectiveName == null ? 43 : $objectiveName.hashCode());
        String $displayName = this.getDisplayName();
        result = result * 59 + ($displayName == null ? 43 : $displayName.hashCode());
        String $criteriaName = this.getCriteriaName();
        result = result * 59 + ($criteriaName == null ? 43 : $criteriaName.hashCode());
        result = result * 59 + this.getSortOrder();
        return result;
    }

    public String toString() {
        return "SetObjectivePacket(displaySlot=" + this.getDisplaySlot() + ", objectiveName=" + this.getObjectiveName() + ", displayName=" + this.getDisplayName() + ", criteriaName=" + this.getCriteriaName() + ", sortOrder=" + this.getSortOrder() + ")";
    }
}

