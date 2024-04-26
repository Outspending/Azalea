package me.outspending.protocol.packets.status.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record PingResponsePacket(long payload) implements Packet {
    public static PingResponsePacket of(@NotNull PacketReader reader) {
        return new PingResponsePacket(reader.readLong());
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
