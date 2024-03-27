package me.outspending.protocol.packets.status.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record StatusRequestPacket() implements Packet {
    public static @NotNull StatusRequestPacket of(PacketReader reader) {
        return new StatusRequestPacket();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {}

    @Override
    public int getID() {
        return 0x00;
    }
}
