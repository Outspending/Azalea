package me.outspending.protocol.packets.client.configuration;

import me.outspending.NamespacedID;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public record ClientRegistryDataPacket(@NotNull NamespacedID registryID, @NotNull List<? extends RegistryType> types) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNamespacedKey(registryID);
        writer.writeVarInt(types.size());
        for (RegistryType type : types) {
            writer.writeNamespacedKey(type.getRegistryID());
            writer.writeBoolean(true);
            writer.writeNBTCompound(type.toNBT());
        }
    }

    @Override
    public int id() {
        return 0x07;
    }

}
