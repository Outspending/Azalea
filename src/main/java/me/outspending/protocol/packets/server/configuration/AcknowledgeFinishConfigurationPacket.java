package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record AcknowledgeFinishConfigurationPacket() implements ServerPacket {

    public static AcknowledgeFinishConfigurationPacket read(PacketReader reader) {
        return new AcknowledgeFinishConfigurationPacket();
    }

    @Override
    public int id() {
        return 0x02;
    }

}
