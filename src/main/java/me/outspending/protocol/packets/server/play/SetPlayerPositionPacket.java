package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetPlayerPositionPacket(ClientConnection connection, Pos position, boolean onGround) implements ServerPacket {
    public static SetPlayerPositionPacket read(ClientConnection connection, PacketReader reader) {
        return new SetPlayerPositionPacket(
                connection,
                new Pos(
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readDouble(),
                        0f, 0f
                ),
                reader.readBoolean()
        );
    }

    @Override
    public int id() {
        return 0x17;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
