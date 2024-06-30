package me.outspending.protocol.packets.client.play;

import me.outspending.position.Angle;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientUpdateEntityPositionAndRotationPacket(int entityID, short deltaX, short deltaY, short deltaZ, @NotNull Angle yaw, @NotNull Angle pitch, boolean onGround) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityID);
        writer.writeShort(deltaX);
        writer.writeShort(deltaY);
        writer.writeShort(deltaZ);
        writer.writeAngle(yaw);
        writer.writeAngle(pitch);
        writer.writeBoolean(onGround);
    }

    @Override
    public int id() {
        return 0x2F;
    }

}
