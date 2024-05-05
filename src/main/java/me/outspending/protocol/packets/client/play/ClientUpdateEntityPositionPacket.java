package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientUpdateEntityPositionPacket(int entityID, short deltaX, short deltaY, short deltaZ, boolean onGround) implements ClientPacket {
    public static ClientUpdateEntityPositionPacket of(@NotNull PacketReader reader) {
        return new ClientUpdateEntityPositionPacket(
            reader.readVarInt(),
            reader.readShort(),
            reader.readShort(),
            reader.readShort(),
            reader.readBoolean()
        );
    }

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
        return 0x2C;
    }
}
