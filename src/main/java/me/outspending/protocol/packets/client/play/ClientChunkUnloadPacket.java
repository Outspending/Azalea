package me.outspending.protocol.packets.client.play;

import me.outspending.chunk.Chunk;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientChunkUnloadPacket(@NotNull Chunk chunk) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeInt(this.chunk.getChunkX());
        writer.writeInt(this.chunk.getChunkZ());
    }

    @Override
    public int id() {
        return 0x21;
    }

}
