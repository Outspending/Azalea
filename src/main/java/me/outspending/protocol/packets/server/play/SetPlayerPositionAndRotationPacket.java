package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetPlayerPositionAndRotationPacket(ClientConnection connection, Pos position, boolean onGround) implements ServerPacket {
    public static SetPlayerPositionAndRotationPacket read(ClientConnection connection, PacketReader reader) {
        return new SetPlayerPositionAndRotationPacket(
                connection,
                new Pos(
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readFloat(),
                        reader.readFloat()
                ),
                reader.readBoolean()
        );
    }

    @Override
    public int id() {
        return 0x18;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
