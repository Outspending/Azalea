package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientGameEventPacket(byte event, float value) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeByte(this.event);
        writer.writeFloat(this.value);
    }

    @Override
    public int id() {
        return 0x22;
    }

}
