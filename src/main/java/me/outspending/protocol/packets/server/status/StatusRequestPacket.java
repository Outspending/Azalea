package me.outspending.protocol.packets.server.status;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record StatusRequestPacket(@NotNull ClientConnection connection) implements ServerPacket {
    public static StatusRequestPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new StatusRequestPacket(connection);
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
