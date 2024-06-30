package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ConfigurationPongPacket(int ping) implements ServerPacket {

    public static @NotNull ConfigurationPongPacket read(@NotNull PacketReader reader) {
        return new ConfigurationPongPacket(reader.readInt());
    }

    @Override
    public int id() {
        return 0x03;
    }

}
