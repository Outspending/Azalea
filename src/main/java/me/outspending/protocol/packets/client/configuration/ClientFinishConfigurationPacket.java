package me.outspending.protocol.packets.client.configuration;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientFinishConfigurationPacket() implements ClientPacket {
    public static ClientFinishConfigurationPacket of(@NotNull PacketReader reader) {
        return new ClientFinishConfigurationPacket();
    }

    @Override
    public void write(PacketWriter writer) {}

    @Override
    public int id() {
        return 0x02;
    }
}
