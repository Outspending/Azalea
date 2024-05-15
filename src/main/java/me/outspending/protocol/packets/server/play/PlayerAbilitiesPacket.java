package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerAbilitiesPacket(@NotNull ClientConnection connection, byte flags) implements ServerPacket {
    public static PlayerAbilitiesPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        return new PlayerAbilitiesPacket(connection, reader.readByte());
    }

    @Override
    public int id() {
        return 0x20;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
