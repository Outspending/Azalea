package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientConfigurationPingPacket(int id) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeInt(id);
    }

}
