package me.outspending.protocol.packets.client.play;

import me.outspending.entity.metadata.EntityMeta;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetEntityMetadataPacket(int entityID, EntityMeta entityMeta) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {

    }

    @Override
    public int id() {
        return 0x56;
    }

}
