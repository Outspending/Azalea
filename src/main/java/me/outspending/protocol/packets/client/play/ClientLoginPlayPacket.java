package me.outspending.protocol.packets.client.play;

import me.outspending.NamespacedID;
import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientLoginPlayPacket(
        int entityID,
        boolean isHardcore,
        NamespacedID[] dimensionNames,
        int maxPlayers,
        int viewDistance,
        int simulationDistance,
        boolean reducedDebugInfo,
        boolean enableRespawnScreen,
        boolean limitedCrafting,
        int dimensionType,
        NamespacedID dimensionName,
        long hashedSeed,
        byte gameMode,
        byte previousGameMode,
        boolean isDebugWorld,
        boolean isFlatWorld,
        boolean hasDeathLocation,
        String deathDimensionName,
        Pos deathPosition,
        int portalCooldown,
        boolean enforceSecureChat
) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeInt(this.entityID);
        writer.writeBoolean(this.isHardcore);
        writer.writeVarInt(this.dimensionNames.length);

        for (NamespacedID dimensionName : this.dimensionNames) {
            writer.writeNamespacedKey(dimensionName);
        }

        writer.writeVarInt(this.maxPlayers);
        writer.writeVarInt(this.viewDistance);
        writer.writeVarInt(this.simulationDistance);
        writer.writeBoolean(this.reducedDebugInfo);
        writer.writeBoolean(this.enableRespawnScreen);
        writer.writeBoolean(this.limitedCrafting);
        writer.writeVarInt(this.dimensionType);
        writer.writeNamespacedKey(this.dimensionName);
        writer.writeLong(this.hashedSeed);
        writer.writeByte(this.gameMode);
        writer.writeByte(this.previousGameMode);
        writer.writeBoolean(this.isDebugWorld);
        writer.writeBoolean(this.isFlatWorld);
        writer.writeBoolean(this.hasDeathLocation);
        if (hasDeathLocation) {
            writer.writeString(this.deathDimensionName);
            writer.writePosition(this.deathPosition);
        }
        writer.writeVarInt(this.portalCooldown);
        writer.writeBoolean(this.enforceSecureChat);
    }

    @Override
    public int id() {
        return 0x2B;
    }

}
