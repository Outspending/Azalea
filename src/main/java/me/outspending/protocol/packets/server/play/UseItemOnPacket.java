package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record UseItemOnPacket(ClientConnection connection, int hand, Location position, int face, float cursorPosX, float cursorPosY, float cursorPosZ, boolean insideBlock, int sequence) implements ServerPacket {
    public static UseItemOnPacket read(ClientConnection connection, PacketReader reader) {
        return new UseItemOnPacket(
                connection,
                reader.readVarInt(),
                reader.readLocation(),
                reader.readVarInt(),
                reader.readFloat(),
                reader.readFloat(),
                reader.readFloat(),
                reader.readBoolean(),
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x35;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
