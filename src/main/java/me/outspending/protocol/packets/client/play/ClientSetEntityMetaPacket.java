package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Entity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetEntityMetaPacket(@NotNull Entity entity) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.entity.getEntityID());
        this.entity.getEntityMeta().write(writer);
    }

    @Override
    public int id() {
        return 0x58;
    }

}
