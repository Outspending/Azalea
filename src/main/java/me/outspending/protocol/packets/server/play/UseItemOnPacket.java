package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record UseItemOnPacket(int hand, @NotNull Pos position, int face, float cursorPosX, float cursorPosY, float cursorPosZ, boolean insideBlock, int sequence) implements ServerPacket {

    public static UseItemOnPacket read(PacketReader reader) {
        return new UseItemOnPacket(
                reader.readVarInt(),
                reader.readPosition(),
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

}
