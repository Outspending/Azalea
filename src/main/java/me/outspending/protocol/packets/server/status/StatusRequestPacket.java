package me.outspending.protocol.packets.server.status;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public class StatusRequestPacket extends ServerPacket {
    public static @NotNull StatusRequestPacket of(PacketReader reader) {
        return new StatusRequestPacket();
    }

    public StatusRequestPacket() {
        super(0x00);
    }
}
