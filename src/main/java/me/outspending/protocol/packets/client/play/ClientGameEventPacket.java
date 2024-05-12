package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
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
        return 0x20;
    }

}
