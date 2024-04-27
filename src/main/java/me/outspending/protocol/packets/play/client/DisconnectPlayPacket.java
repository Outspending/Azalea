package me.outspending.protocol.packets.play.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record DisconnectPlayPacket(CompoundBinaryTag reason) implements Packet {
    public static @NotNull DisconnectPlayPacket of(@NotNull PacketReader reader) {
        return new DisconnectPlayPacket(reader.readNBTCompound());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(reason);
    }

    @Override
    public int getID() {
        return 0x1B;
    }
}
