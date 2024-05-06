package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientRemoveEntitiesPacket(int entityCount, int... entityIds) implements ClientPacket {
    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entityCount);
        for (int entityID : entityIds) {
            writer.writeVarInt(entityID);
        }
    }

    @Override
    public int id() {
        return 0x40;
    }
}
