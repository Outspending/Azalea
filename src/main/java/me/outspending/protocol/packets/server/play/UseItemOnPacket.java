package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record UseItemOnPacket(@NotNull ClientConnection connection, int hand, @NotNull Location position, int face, float cursorPosX, float cursorPosY, float cursorPosZ, boolean insideBlock, int sequence) implements ServerPacket {
    public static UseItemOnPacket read(@NotNull ClientConnection connection, PacketReader reader) {
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
