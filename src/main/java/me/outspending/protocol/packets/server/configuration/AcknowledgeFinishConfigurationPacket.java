package me.outspending.protocol.packets.server.configuration;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class AcknowledgeFinishConfigurationPacket extends ServerPacket {
    public static AcknowledgeFinishConfigurationPacket of(@NotNull PacketReader reader) {
        return new AcknowledgeFinishConfigurationPacket();
    }

    public AcknowledgeFinishConfigurationPacket() {
        super(0x02);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {}
}
