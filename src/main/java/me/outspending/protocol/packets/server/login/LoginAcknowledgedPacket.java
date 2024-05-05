package me.outspending.protocol.packets.server.login;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record LoginAcknowledgedPacket() implements ServerPacket {
    public static LoginAcknowledgedPacket read(PacketReader reader) {
        return new LoginAcknowledgedPacket();
    }

    @Override
    public int id() {
        return 0x03;
    }
}
