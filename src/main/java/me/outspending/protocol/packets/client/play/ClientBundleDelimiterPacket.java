package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;

public record ClientBundleDelimiterPacket() implements ClientPacket {
    @Override
    public void write(PacketWriter writer) {}

    @Override
    public int id() {
        return 0x00;
    }
}
