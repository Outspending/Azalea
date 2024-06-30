package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Entity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientHurtAnimationPacket(@NotNull Entity entity, float yaw) implements ClientPacket {
    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.entity.getEntityID());
        writer.writeFloat(this.yaw);
    }

    @Override
    public int id() {
        return 0x24;
    }
}
