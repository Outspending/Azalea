package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientClearTitlesPacket(boolean reset) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeBoolean(this.reset);
    }

    @Override
    public int id() {
        return 0x0F;
    }

}
