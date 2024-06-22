package me.outspending.protocol.packets.client.configuration;

import me.outspending.NamespacedID;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ClientRegistryDataPacket(@NotNull NamespacedID registryID, @NotNull RegistryType... types) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNamespacedKey(registryID);
        writer.writeVarInt(types.length);
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
