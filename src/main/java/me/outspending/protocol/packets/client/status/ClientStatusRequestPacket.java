package me.outspending.protocol.packets.client.status;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;

public class ClientStatusRequestPacket extends ClientPacket {
    public static @NotNull ClientStatusRequestPacket of(PacketReader reader) {
        return new ClientStatusRequestPacket();
    }

    public ClientStatusRequestPacket() {
        super(0x00);
    }
}
