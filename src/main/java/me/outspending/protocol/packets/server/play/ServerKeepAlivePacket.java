package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ServerKeepAlivePacket(@NotNull ClientConnection connection, long keepAliveID) implements ServerPacket {
    public static ServerKeepAlivePacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new ServerKeepAlivePacket(connection, reader.readLong());
    }

    @Override
    public int id() {
        return 0x15;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
