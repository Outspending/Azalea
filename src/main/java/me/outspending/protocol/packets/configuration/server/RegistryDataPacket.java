package me.outspending.protocol.packets.configuration.server;

import me.nullicorn.nedit.type.NBTCompound;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record RegistryDataPacket(NBTCompound registry) implements Packet {
    public static RegistryDataPacket of(@NotNull PacketReader reader) {
        return new RegistryDataPacket(reader.readNBTCompound());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(registry);
    }

    @Override
    public int getID() {
        return 0x05;
    }
}
