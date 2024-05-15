package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetHeldItemPacket(@NotNull ClientConnection connection, short slot) implements ServerPacket {
    public static SetHeldItemPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new SetHeldItemPacket(connection, reader.readShort());
    }

    @Override
    public int id() {
        return 0x2C;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
