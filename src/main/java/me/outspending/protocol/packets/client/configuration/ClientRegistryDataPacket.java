package me.outspending.protocol.packets.client.configuration;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ClientRegistryDataPacket(CompoundBinaryTag compound) implements ClientPacket {
    public ClientRegistryDataPacket(CompoundBinaryTag compound) {
        this.compound = compound;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(this.compound);
    }

    @Override
    public int id() {
        return 0x05;
    }

}
