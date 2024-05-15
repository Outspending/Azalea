package me.outspending.protocol.packets.server.login;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record LoginAcknowledgedPacket(@NotNull ClientConnection connection) implements ServerPacket {
    public static LoginAcknowledgedPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new LoginAcknowledgedPacket(connection);
    }

    @Override
    public int id() {
        return 0x03;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
