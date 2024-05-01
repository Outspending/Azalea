package me.outspending.protocol.packets.server.status;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record StatusRequestPacket() implements ServerPacket {
    public static StatusRequestPacket read(PacketReader reader) {
        return new StatusRequestPacket();
    }

    @Override
    public int id() {
        return 0x00;
    }
}
