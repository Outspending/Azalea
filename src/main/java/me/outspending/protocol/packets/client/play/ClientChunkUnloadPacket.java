package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;

public record ClientChunkUnloadPacket(int chunkX, int chunkZ) implements ClientPacket {
    @Override
    public void write(PacketWriter writer) {
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);
    }

    @Override
    public int id() {
        return 0x1F;
    }
}
