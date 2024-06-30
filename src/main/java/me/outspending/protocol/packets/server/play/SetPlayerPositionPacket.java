package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetPlayerPositionPacket(@NotNull Pos position, boolean onGround) implements ServerPacket {

    public static SetPlayerPositionPacket read(@NotNull PacketReader reader) {
        return new SetPlayerPositionPacket(
                reader.readPosition(),
                reader.readBoolean()
        );
    }

    @Override
    public int id() {
        return 0x17;
    }

}
