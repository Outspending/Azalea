package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientCenterChunkPacket(int chunkX, int chunkZ) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.chunkX);
        writer.writeVarInt(this.chunkZ);
    }

    @Override
    public int id() {
        return 0x54;
    }

}
