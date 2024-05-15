package me.outspending.protocol.packets.server;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record HandshakePacket(@NotNull ClientConnection connection, int protocolVersion, @NotNull String serverAddress, short serverPort, int nextState) implements ServerPacket {
    public static HandshakePacket read(@NotNull ClientConnection connection, PacketReader reader) {
        return new HandshakePacket(
                connection,
                reader.readVarInt(),
                reader.readString(),
                reader.readShort(),
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x00;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
