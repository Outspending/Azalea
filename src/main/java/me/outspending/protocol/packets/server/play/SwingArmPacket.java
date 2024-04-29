package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SwingArmPacket(int hand) implements ServerPacket {
    public static SwingArmPacket read(PacketReader reader) {
        return new SwingArmPacket(reader.readVarInt());
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x33;
    }
}
