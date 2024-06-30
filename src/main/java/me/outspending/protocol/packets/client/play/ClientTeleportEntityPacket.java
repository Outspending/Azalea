package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Entity;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientTeleportEntityPacket(@NotNull Entity entity, @NotNull Pos pos) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entity.getEntityID());
        writer.writeDouble(pos.x());
        writer.writeDouble(pos.y());
        writer.writeDouble(pos.z());
        writer.writeByte((byte) pos.yaw());
        writer.writeByte((byte) pos.pitch());
        writer.writeBoolean(entity.isOnGround());
    }

    @Override
    public int id() {
        return 0x70;
    }

}
