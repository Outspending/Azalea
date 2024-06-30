package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PluginMessageConfigurationPacket(@NotNull String channel, byte[] data) implements ServerPacket {

    public static PluginMessageConfigurationPacket read(@NotNull PacketReader reader) {
        return new PluginMessageConfigurationPacket(
                reader.readString(),
                reader.readByteArray()
        );
    }

    @Override
    public int id() {
        return 0x01;
    }

}
