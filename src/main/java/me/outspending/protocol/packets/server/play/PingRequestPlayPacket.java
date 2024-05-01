package me.outspending.protocol.packets.server.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PingRequestPlayPacket(long payload) implements ServerPacket {
    public static PingRequestPlayPacket read(@NotNull PacketReader reader) {
        return new PingRequestPlayPacket(reader.readLong());
    }

    @Override
    public int id() {
        return 0x1E;
    }
}
