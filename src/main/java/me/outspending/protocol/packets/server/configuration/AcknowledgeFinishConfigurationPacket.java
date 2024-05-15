package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record AcknowledgeFinishConfigurationPacket(@NotNull ClientConnection connection) implements ServerPacket {
    public static AcknowledgeFinishConfigurationPacket read(ClientConnection connection, PacketReader reader) {
        return new AcknowledgeFinishConfigurationPacket(connection);
    }

    @Override
    public int id() {
        return 0x02;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
