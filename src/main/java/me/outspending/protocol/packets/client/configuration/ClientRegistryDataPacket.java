package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ClientRegistryDataPacket(@NotNull CompoundBinaryTag compound) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(this.compound);
    }

    @Override
    public int id() {
        return 0x05;
    }

}
