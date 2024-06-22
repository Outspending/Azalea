package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientKeepAlivePacket(long keepAliveID) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(keepAliveID);
    }

    @Override
    public int id() {
        return 0x18;
    }

}
