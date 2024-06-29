package me.outspending.protocol.packets.client.play;

import me.outspending.NamespacedID;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientStoreCookiePacket(@NotNull NamespacedID key, byte @NotNull [] data) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNamespacedKey(this.key);
        writer.writeVarInt(data.length);
        writer.writeByteArray(data);
    }

    @Override
    public int id() {
        return 0x6B;
    }

}
