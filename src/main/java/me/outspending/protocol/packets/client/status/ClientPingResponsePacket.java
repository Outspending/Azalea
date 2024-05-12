package me.outspending.protocol.packets.client.status;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientPingResponsePacket(long payload) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(this.payload);
    }

    @Override
    public int id() {
        return 0x01;
    }

}
