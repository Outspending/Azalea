package me.outspending.protocol.packets.login.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record SetCompressionPacket(int threshold) implements Packet {
    public static @NotNull SetCompressionPacket of(@NotNull PacketReader reader) {
        return new SetCompressionPacket(reader.readVarInt());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(threshold);
    }

    @Override
    public int getID() {
        return 0x03;
    }
}
