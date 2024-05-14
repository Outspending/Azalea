package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerActionPacket(ClientConnection connection, int status, Location position, byte face, int sequence) implements ServerPacket {
    public static PlayerActionPacket read(ClientConnection connection, PacketReader reader) {
        return new PlayerActionPacket(
                connection,
                reader.readVarInt(),
                reader.readLocation(),
                reader.readByte(),
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x21;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
