package me.outspending.protocol.packets.server.status;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PingRequestPacket(long payload) implements ServerPacket {

    public static PingRequestPacket read(@NotNull PacketReader reader) {
        return new PingRequestPacket(reader.readLong());
    }

    @Override
    public int id() {
        return 0x01;
    }

}
