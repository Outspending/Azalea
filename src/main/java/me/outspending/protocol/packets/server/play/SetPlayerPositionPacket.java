package me.outspending.protocol.packets.server.play;

import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;

public record SetPlayerPositionPacket(Pos position, boolean isGround) implements ServerPacket {
    public static SetPlayerPositionPacket read(PacketReader reader) {
        return new SetPlayerPositionPacket(
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
}
