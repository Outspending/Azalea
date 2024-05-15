package me.outspending.protocol.packets.client.play;

import me.outspending.position.Angle;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetHeadRotationPacket(int entityID, Angle headRotation) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityID);
        writer.writeAngle(headRotation);
    }

    @Override
    public int id() {
        return 0x46;
    }

}