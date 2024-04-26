package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record RegistryDataPacket(CompoundBinaryTag compound) implements Packet {
    public static RegistryDataPacket of(@NotNull PacketReader reader) {
        return new RegistryDataPacket(reader.readNBTCompound());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(compound);
    }

    @Override
    public int getID() {
        return 0x05;
    }
}
