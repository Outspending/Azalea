package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record FinishConfigurationPacket() implements Packet {
    public static FinishConfigurationPacket of(@NotNull PacketReader reader) {
        return new FinishConfigurationPacket();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
    }

    @Override
    public int getID() {
        return 0x02;
    }
}
