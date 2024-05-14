package me.outspending.protocol.packets.server.status;

import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PingRequestPacket(ClientConnection connection, long payload) implements ServerPacket {
    public static PingRequestPacket read(ClientConnection connection, PacketReader reader) {
        return new PingRequestPacket(connection, reader.readLong());
    }

    @Override
    public int id() {
        return 0x01;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
