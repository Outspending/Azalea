package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientUpdateEntityPositionPacket(int entityID, short deltaX, short deltaY, short deltaZ, boolean onGround) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityID);
        writer.writeShort(deltaX);
        writer.writeShort(deltaY);
        writer.writeShort(deltaZ);
        writer.writeBoolean(onGround);
    }

    @Override
    public int id() {
        return 0x30;
    }

}
