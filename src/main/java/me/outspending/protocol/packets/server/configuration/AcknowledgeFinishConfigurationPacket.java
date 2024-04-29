package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record AcknowledgeFinishConfigurationPacket() implements ServerPacket {
    public static AcknowledgeFinishConfigurationPacket read(PacketReader reader) {
        return new AcknowledgeFinishConfigurationPacket();
    }

    @Override
    public @NotNull GameState state() {
        return GameState.CONFIGURATION;
    }

    @Override
    public int id() {
        return 0x02;
    }
}
