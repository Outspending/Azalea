package me.outspending.protocol.packets.client.status;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientPingResponsePacket(long payload) implements ClientPacket {
    public static ClientPingResponsePacket of(@NotNull PacketReader reader) {
        return new ClientPingResponsePacket(reader.readLong());
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLong(this.payload);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.STATUS;
    }

    @Override
    public int id() {
        return 0x01;
    }
}
