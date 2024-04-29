package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetHeldItemPacket(short slot) implements ServerPacket {
    public static SetHeldItemPacket read(PacketReader reader) {
        return new SetHeldItemPacket(reader.readShort());
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x2C;
    }
}
