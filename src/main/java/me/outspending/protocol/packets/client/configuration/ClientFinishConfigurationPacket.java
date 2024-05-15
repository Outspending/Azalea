package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientFinishConfigurationPacket() implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {}

    @Override
    public int id() {
        return 0x02;
    }

}
