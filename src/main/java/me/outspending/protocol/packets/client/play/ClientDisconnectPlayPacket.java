package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientDisconnectPlayPacket extends ClientPacket {
    private final CompoundBinaryTag reason;

    public static @NotNull ClientDisconnectPlayPacket of(@NotNull PacketReader reader) {
        return new ClientDisconnectPlayPacket(reader.readNBTCompound());
    }

    public ClientDisconnectPlayPacket(CompoundBinaryTag reason) {
        super(0x1B);
        this.reason = reason;
    }
}
