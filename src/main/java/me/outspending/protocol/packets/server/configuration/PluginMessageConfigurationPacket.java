package me.outspending.protocol.packets.server.configuration;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class PluginMessageConfigurationPacket extends ServerPacket {
    private final String channel;
    private final byte[] data;

    public static PluginMessageConfigurationPacket of(@NotNull PacketReader reader) {
        return new PluginMessageConfigurationPacket(
                reader.readString(),
                reader.readByteArray()
        );
    }

    public PluginMessageConfigurationPacket(String channel, byte[] data) {
        super(0x01);
        this.channel = channel;
        this.data = data;
    }
}
