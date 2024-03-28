package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ConfigurationDisconnectPacket(String value) implements Packet {
    public static ConfigurationDisconnectPacket of(PacketReader reader) {
        return new ConfigurationDisconnectPacket(reader.readString());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(value);
    }

    @Override
    public int getID() {
        return 0x01;
    }
}
