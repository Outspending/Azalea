package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public record ClientPlayerInfoRemovePacket(@NotNull Collection<UUID> uuids) implements ClientPacket {
    public ClientPlayerInfoRemovePacket(@NotNull UUID... uuids) {
        this(Arrays.asList(uuids));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(uuids.size());
        for (UUID uuid : uuids) {
            writer.writeUUID(uuid);
        }
    }

    @Override
    public int id() {
        return 0x3D;
    }

}
