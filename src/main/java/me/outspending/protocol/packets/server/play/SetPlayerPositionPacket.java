package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x17;
    }
}
