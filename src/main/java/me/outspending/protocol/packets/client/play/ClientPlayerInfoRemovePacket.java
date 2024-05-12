package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientPlayerInfoRemovePacket(int numOfPlayers, UUID... uuids) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(numOfPlayers);
        writer.writeArray(uuids, writer::writeUUID);
    }

    @Override
    public int id() {
        return 0x3B;
    }

}
