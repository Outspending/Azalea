package me.outspending.protocol.packets.client.status;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientPingResponsePacket extends ClientPacket {
    private final long payload;

    public static ClientPingResponsePacket of(@NotNull PacketReader reader) {
        return new ClientPingResponsePacket(reader.readLong());
    }

    public ClientPingResponsePacket(long payload) {
        super(0x01);
        this.payload = payload;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLong(this.payload);
    }
}
