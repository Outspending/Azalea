package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PluginMessageConfigurationPacket(ClientConnection connection, String channel, byte[] data) implements ServerPacket {
    public static PluginMessageConfigurationPacket read(ClientConnection connection, PacketReader reader) {
        return new PluginMessageConfigurationPacket(
                connection,
                reader.readString(),
                reader.readByteArray()
        );
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
