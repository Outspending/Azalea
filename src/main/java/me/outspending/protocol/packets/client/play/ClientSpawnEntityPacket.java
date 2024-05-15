package me.outspending.protocol.packets.client.play;

import me.outspending.position.Angle;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientSpawnEntityPacket(
        int entityID,
        UUID entityUUID,
        int type,
        double x,
        double y,
        double z,
        Angle pitch,
        Angle yaw,
        Angle headYaw,
        int data,
        short velocityX,
        short velocityY,
        short velocityZ
) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityID);
        writer.writeUUID(entityUUID);
        writer.writeVarInt(type);
        writer.writeDouble(x);
        writer.writeDouble(y);
        writer.writeDouble(z);
        writer.writeAngle(pitch);
        writer.writeAngle(yaw);
        writer.writeAngle(headYaw);
        writer.writeVarInt(data);
        writer.writeShort(velocityX);
        writer.writeShort(velocityY);
        writer.writeShort(velocityZ);
    }

    @Override
    public int id() {
        return 0x01;
    }

}
