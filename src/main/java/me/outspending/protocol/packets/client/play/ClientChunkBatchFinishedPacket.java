package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientChunkBatchFinishedPacket(int batchSize) implements ClientPacket {
    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(batchSize);
    }

    @Override
    public int id() {
        return 0x0C;
    }
}
