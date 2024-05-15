package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SwingArmPacket(@NotNull ClientConnection connection, int hand) implements ServerPacket {
    public static SwingArmPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new SwingArmPacket(connection, reader.readVarInt());
    }

    @Override
    public int id() {
        return 0x33;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
