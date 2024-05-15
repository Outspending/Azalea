package me.outspending.protocol.packets.client.login;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetCompressionPacket(int threshold) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.threshold);
    }

    @Override
    public int id() {
        return 0x03;
    }

}
