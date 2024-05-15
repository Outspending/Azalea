package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public record ClientPlayerInfoRemovePacket(int numOfPlayers, @NotNull Collection<UUID> uuids) implements ClientPacket {
    public ClientPlayerInfoRemovePacket(int numOfPlayers, @NotNull UUID... uuids) {
        this(numOfPlayers, Arrays.asList(uuids));
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
        return 0x3B;
    }

}
