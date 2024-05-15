package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PingRequestPlayPacket(@NotNull ClientConnection connection, long payload) implements ServerPacket {
    public static PingRequestPlayPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new PingRequestPlayPacket(connection, reader.readLong());
    }

    @Override
    public int id() {
        return 0x1E;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
