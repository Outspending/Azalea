package me.outspending.protocol.packets.server.status;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class PingRequestPacket extends ServerPacket {
    private final long payload;

    public static PingRequestPacket of(@NotNull PacketReader reader) {
        return new PingRequestPacket(reader.readLong());
    }

    public PingRequestPacket(long payload) {
        super(0x01);
        this.payload = payload;
    }
}
