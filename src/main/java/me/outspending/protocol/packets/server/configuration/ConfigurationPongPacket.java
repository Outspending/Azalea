package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ConfigurationPongPacket(@NotNull ClientConnection connection, int id) implements ServerPacket {
    public static @NotNull ConfigurationPongPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new ConfigurationPongPacket(connection, reader.readInt());
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return this.connection;
    }

}
