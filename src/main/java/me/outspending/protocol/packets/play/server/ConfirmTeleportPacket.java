package me.outspending.protocol.packets.play.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ConfirmTeleportPacket(int teleportID) implements Packet {
    public static ConfirmTeleportPacket of(@NotNull PacketReader reader) {
        return new ConfirmTeleportPacket(
                reader.readVarInt()
        );
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.teleportID);
    }

    @Override
    public int getID() {
        return 0x00;
    }
}
