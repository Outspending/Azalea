package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ConfirmTeleportPacket(@NotNull ClientConnection connection, int teleportID) implements ServerPacket {
    public static ConfirmTeleportPacket read(@NotNull ClientConnection connection, PacketReader reader) {
        return new ConfirmTeleportPacket(
                connection,
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x00;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
