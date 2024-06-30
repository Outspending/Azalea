package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Entity;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientSpawnEntityPacket(@NotNull Entity entity) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        final Pos pos = this.entity.getPosition();

        writer.writeVarInt(this.entity.getEntityID());
        writer.writeUUID(this.entity.getUuid());
        writer.writeVarInt(this.entity.getType().getId());
        writer.writeDouble(pos.x());
        writer.writeDouble(pos.y());
        writer.writeDouble(pos.z());
        writer.writeAngle(new Angle(pos.pitch()));
        writer.writeAngle(new Angle(pos.yaw()));
        writer.writeAngle(new Angle(0));
        writer.writeVarInt(0);
        writer.writeShort((short) 0);
        writer.writeShort((short) 0);
        writer.writeShort((short) 0);
    }

    @Override
    public int id() {
        return 0x01;
    }

}
