package me.outspending.protocol.packets.server.status;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class PingResponsePacket extends ServerPacket {
    private final long payload;

    public static PingResponsePacket of(@NotNull PacketReader reader) {
        return new PingResponsePacket(reader.readLong());
    }

    public PingResponsePacket(long payload) {
        super(0x01);
        this.payload = payload;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLong(this.payload);
    }
}
