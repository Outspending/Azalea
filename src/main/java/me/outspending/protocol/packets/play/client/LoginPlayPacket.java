package me.outspending.protocol.packets.play.client;

import me.outspending.GameMode;
import me.outspending.position.Location;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record LoginPlayPacket(
        int entityID,
        boolean isHardcore,
        int dimensionCount,
        String[] dimensionNames, // Might be the issue
        int maxPlayers,
        int viewDistance,
        int simulationDistance,
        boolean isDebug,
        boolean respawnScreen,
        boolean limitedCrafting,
        String dimensionType,
        String dimensionName,
        long seed,
        byte gameMode,
        byte previousGameMode,
        boolean isDebugWorld,
        boolean isFlatWorld,
        boolean hasDeathLocation,
        String deathDimensionName,
        Location deathLocation, // Might be the issue
        int portalCooldown
) implements Packet {
    public static LoginPlayPacket of(@NotNull PacketReader reader) {
        return new LoginPlayPacket(
                reader.readVarInt(),
                reader.readBoolean(),
                reader.readVarInt(),
                reader.readArray(packetReader -> reader.readString(), String[]::new),
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readString(),
                reader.readString(),
                reader.readLong(),
                reader.readByte(),
                reader.readByte(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readString(),
                reader.readLocation(),
                reader.readVarInt()
        );
    }
    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityID);
        writer.writeBoolean(isHardcore);
        writer.writeVarInt(dimensionCount);
        writer.writeArray(dimensionNames, writer::writeString);
        writer.writeVarInt(maxPlayers);
        writer.writeVarInt(viewDistance);
        writer.writeVarInt(simulationDistance);
        writer.writeBoolean(isDebug);
        writer.writeBoolean(respawnScreen);
        writer.writeBoolean(limitedCrafting);
        writer.writeString(dimensionType);
        writer.writeString(dimensionName);
        writer.writeLong(seed);
        writer.write(gameMode);
        writer.write(previousGameMode);
        writer.writeBoolean(isDebugWorld);
        writer.writeBoolean(isFlatWorld);
        writer.writeBoolean(hasDeathLocation);
        writer.writeString(deathDimensionName);
        writer.writeLocation(deathLocation);
        writer.writeVarInt(portalCooldown);
    }

    @Override
    public int getID() {
        return 0x2A;
    }
}
