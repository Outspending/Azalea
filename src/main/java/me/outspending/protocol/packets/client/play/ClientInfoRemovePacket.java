package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientInfoRemovePacket(int numOfPlayers, UUID[] uuids) implements ClientPacket {
    public static ClientInfoRemovePacket of(@NotNull PacketReader reader) {
        return new ClientInfoRemovePacket(
                reader.readVarInt(),
                reader.readArray(PacketReader::readUUID, UUID[]::new)
        );
    }

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
