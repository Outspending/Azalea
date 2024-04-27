package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ConfirmTeleportPacket extends ServerPacket {
    private final int teleportID;

    public static ConfirmTeleportPacket of(@NotNull PacketReader reader) {
        return new ConfirmTeleportPacket(
                reader.readVarInt()
        );
    }

    public ConfirmTeleportPacket(int teleportID) {
        super(0x00);
        this.teleportID = teleportID;
    }
}
