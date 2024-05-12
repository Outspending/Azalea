package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientChunkBatchStartPacket() implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {}

    @Override
    public int id() {
        return 0x0D;
    }

}
