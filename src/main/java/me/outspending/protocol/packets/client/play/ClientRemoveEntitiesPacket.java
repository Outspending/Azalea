package me.outspending.protocol.packets.client.play;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.outspending.entity.Entity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public record ClientRemoveEntitiesPacket(IntList entityIds) implements ClientPacket {

    public ClientRemoveEntitiesPacket(int... entityIDs) {
        this(IntList.of(entityIDs));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.entityIds.size());
        for (int entityID : entityIds) {
            writer.writeVarInt(entityID);
        }
    }

    @Override
    public int id() {
        return 0x42;
    }

}
