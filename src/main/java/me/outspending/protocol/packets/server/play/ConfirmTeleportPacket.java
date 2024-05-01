package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
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
