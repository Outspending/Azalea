package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record PluginMessageConfigurationPacket(String channel, byte[] data) implements Packet {
    public static PluginMessageConfigurationPacket of(@NotNull PacketReader reader) {
        return new PluginMessageConfigurationPacket(
                reader.readString(),
                reader.readByteArray()
        );
    }
    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(channel);
        writer.writeByteArray(data);
    }

    @Override
    public int getID() {
        return 0x01;
    }
}
