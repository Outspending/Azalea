package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ConfigurationPluginMessagePacket(String identifier, byte[] data) implements Packet {
    public static ConfigurationPluginMessagePacket of(PacketReader reader) {
        return new ConfigurationPluginMessagePacket(reader.readString(), reader.readByteArray());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(identifier);
        writer.writeByteArray(data);
    }

    @Override
    public int getID() {
        return 0x02;
    }
}
