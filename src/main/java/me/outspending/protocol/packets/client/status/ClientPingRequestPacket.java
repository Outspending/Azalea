package me.outspending.protocol.packets.client.status;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientPingRequestPacket extends ClientPacket {
    private final long payload;

    public static ClientPingRequestPacket of(@NotNull PacketReader reader) {
        return new ClientPingRequestPacket(reader.readLong());
    }

    public ClientPingRequestPacket(long payload) {
        super(0x01);
        this.payload = payload;
    }
}
