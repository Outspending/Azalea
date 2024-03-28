package me.outspending.protocol.packets.configuration.server;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record RegistryDataPacket(CompoundTag compound) implements Packet {
    public static RegistryDataPacket of(@NotNull PacketReader reader) {
        return new RegistryDataPacket(reader.readAnyTag(CompoundTag.class));
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
