package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.NamespacedID;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientLoginPlayPacket extends ClientPacket {
    private final int entityID;
    private final boolean isHardcore;
    private final int dimensionCount;
    private final NamespacedID[] dimensionNames;
    private final int maxPlayers;
    private final int viewDistance;
    private final int simulationDistance;
    private final boolean isDebug;
    private final boolean respawnScreen;
    private final boolean limitedCrafting;
    private final NamespacedID dimensionType;
    private final NamespacedID dimensionName;
    private final long hashedSeed;
    private final byte gameMode;
    private final byte previousGameMode;
    private final boolean isDebugWorld;
    private final boolean isFlatWorld;
    private final boolean hasDeathLocation;
    private final String deathDimensionName;
    private final Location deathLocation;
    private final int portalCooldown;

    public static ClientLoginPlayPacket of(@NotNull PacketReader reader) {
        return new ClientLoginPlayPacket(
                reader.readInt(),
                reader.readBoolean(),
                reader.readVarInt(),
                reader.readArray(packetReader -> reader.readNamespacedKey(), NamespacedID[]::new),
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readBoolean(),
                reader.readNamespacedKey(),
                reader.readNamespacedKey(),
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

    public ClientLoginPlayPacket(int entityID, boolean isHardcore, int dimensionCount, NamespacedID[] dimensionNames, int maxPlayers, int viewDistance, int simulationDistance, boolean isDebug, boolean respawnScreen, boolean limitedCrafting, NamespacedID dimensionType, NamespacedID dimensionName, long hashedSeed, byte gameMode, byte previousGameMode, boolean isDebugWorld, boolean isFlatWorld, boolean hasDeathLocation, String deathDimensionName, Location deathLocation, int portalCooldown) {
        super(0x29);
        this.entityID = entityID;
        this.isHardcore = isHardcore;
        this.dimensionCount = dimensionCount;
        this.dimensionNames = dimensionNames;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.simulationDistance = simulationDistance;
        this.isDebug = isDebug;
        this.respawnScreen = respawnScreen;
        this.limitedCrafting = limitedCrafting;
        this.dimensionType = dimensionType;
        this.dimensionName = dimensionName;
        this.hashedSeed = hashedSeed;
        this.gameMode = gameMode;
        this.previousGameMode = previousGameMode;
        this.isDebugWorld = isDebugWorld;
        this.isFlatWorld = isFlatWorld;
        this.hasDeathLocation = hasDeathLocation;
        this.deathDimensionName = deathDimensionName;
        this.deathLocation = deathLocation;
        this.portalCooldown = portalCooldown;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeInt(this.entityID);
        writer.writeBoolean(this.isHardcore);
        writer.writeVarInt(this.dimensionCount);
        writer.writeArray(this.dimensionNames, writer::writeNamespacedKey);
        writer.writeVarInt(this.maxPlayers);
        writer.writeVarInt(this.viewDistance);
        writer.writeVarInt(this.simulationDistance);
        writer.writeBoolean(this.isDebug);
        writer.writeBoolean(this.respawnScreen);
        writer.writeBoolean(this.limitedCrafting);
        writer.writeNamespacedKey(this.dimensionType);
        writer.writeNamespacedKey(this.dimensionName);
        writer.writeLong(this.hashedSeed);
        writer.writeByte(this.gameMode);
        writer.writeByte(this.previousGameMode);
        writer.writeBoolean(this.isDebugWorld);
        writer.writeBoolean(this.isFlatWorld);
        writer.writeBoolean(this.hasDeathLocation);
        if (hasDeathLocation) {
            writer.writeString(this.deathDimensionName);
            writer.writeLocation(this.deathLocation);
        }
        writer.writeVarInt(this.portalCooldown);
    }
}
