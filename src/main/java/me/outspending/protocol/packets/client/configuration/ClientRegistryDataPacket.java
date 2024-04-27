package me.outspending.protocol.packets.client.configuration;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientRegistryDataPacket extends ClientPacket {
    private final CompoundBinaryTag compound;

    public static ClientRegistryDataPacket of(@NotNull PacketReader reader) {
        return new ClientRegistryDataPacket(reader.readNBTCompound());
    }

    public ClientRegistryDataPacket(CompoundBinaryTag compound) {
        super(0x05);
        this.compound = compound;
    }
}
