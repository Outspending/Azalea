package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ClientDisconnectPlayPacket(@NotNull CompoundBinaryTag reason) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNBTCompound(this.reason);
    }

    @Override
    public int id() {
        return 0x1B;
    }

}
