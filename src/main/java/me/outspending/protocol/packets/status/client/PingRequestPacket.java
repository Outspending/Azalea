package me.outspending.protocol.packets.status.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record PingRequestPacket(long payload) implements Packet {
    public static PingRequestPacket of(@NotNull PacketReader reader) {
        return new PingRequestPacket(reader.readLong());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(payload);
    }

    @Override
    public int getID() {
        return 0x01;
    }
}
