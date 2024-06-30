package me.outspending.protocol.packets.client.play;

import me.outspending.NamespacedID;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientRequestCookiePacket(@NotNull NamespacedID key) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNamespacedKey(this.key);
    }

    @Override
    public int id() {
        return 0x16;
    }

}
