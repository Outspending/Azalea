package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record AcknowledgeFinishConfigurationPacket() implements Packet {
    public static AcknowledgeFinishConfigurationPacket of(@NotNull PacketReader reader) {
        return new AcknowledgeFinishConfigurationPacket();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

    }

    @Override
    public int getID() {
        return 0x02;
    }
}
