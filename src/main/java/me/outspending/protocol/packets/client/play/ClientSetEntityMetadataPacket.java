package me.outspending.protocol.packets.client.play;

import me.outspending.entity.metadata.Metadata;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetEntityMetadataPacket(int entityID, @NotNull Metadata entityMeta) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        // TODO: Write Metadata
    }

    @Override
    public int id() {
        return 0x56;
    }

}
