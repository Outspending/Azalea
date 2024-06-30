package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ConfirmTeleportPacket(int teleportID) implements ServerPacket {

    public static ConfirmTeleportPacket read(PacketReader reader) {
        return new ConfirmTeleportPacket(
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x00;
    }

}
