package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetPlayerPositionAndRotationPacket(Pos position, boolean onGround) implements ServerPacket {
    public static SetPlayerPositionAndRotationPacket read(PacketReader reader) {
        return new SetPlayerPositionAndRotationPacket(
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
}
