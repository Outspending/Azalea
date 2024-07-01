package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientPingResponsePlayPacket(long payload) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(this.payload);
    }

    @Override
    public int id() {
        return 0x36;
    }

}
