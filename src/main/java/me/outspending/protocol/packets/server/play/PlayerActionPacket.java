package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerActionPacket(int status, @NotNull Pos pos, byte face, int sequence) implements ServerPacket {

    public static PlayerActionPacket read(@NotNull PacketReader reader) {
        return new PlayerActionPacket(
                reader.readVarInt(),
                reader.readPosition(),
                reader.readByte(),
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x21;
    }

}
