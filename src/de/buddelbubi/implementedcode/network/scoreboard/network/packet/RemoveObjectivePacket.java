/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.network.protocol.DataPacket
 */
package de.buddelbubi.implementedcode.network.scoreboard.network.packet;

import cn.nukkit.network.protocol.DataPacket;

public class RemoveObjectivePacket
extends DataPacket {
    public static final byte NETWORK_ID = 106;
    public String objectiveName;

    public byte pid() {
        return 106;
    }

    public void decode() {
    }

    public void encode() {
        this.reset();
        this.putString(this.objectiveName);
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RemoveObjectivePacket)) {
            return false;
        }
        RemoveObjectivePacket other = (RemoveObjectivePacket)((Object)o);
        if (!other.canEqual((Object)this)) {
            return false;
        }
        String this$objectiveName = this.getObjectiveName();
        String other$objectiveName = other.getObjectiveName();
        return this$objectiveName == null ? other$objectiveName == null : this$objectiveName.equals(other$objectiveName);
    }

    protected boolean canEqual(Object other) {
        return other instanceof RemoveObjectivePacket;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $objectiveName = this.getObjectiveName();
        result = result * 59 + ($objectiveName == null ? 43 : $objectiveName.hashCode());
        return result;
    }

    public String toString() {
        return "RemoveObjectivePacket(objectiveName=" + this.getObjectiveName() + ")";
    }
}

