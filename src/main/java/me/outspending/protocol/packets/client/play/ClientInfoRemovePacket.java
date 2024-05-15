package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientInfoRemovePacket(int numOfPlayers, UUID @NotNull [] uuids) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(numOfPlayers);
        for (UUID uuid : uuids) {
            writer.writeUUID(uuid);
        }
    }

    @Override
    public int id() {
        return 0x3C;
    }

}
