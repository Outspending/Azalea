package me.outspending.protocol.packets.client.login;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetCompressionPacket(int threshold) implements ClientPacket {
    public static @NotNull ClientSetCompressionPacket of(@NotNull PacketReader reader) {
        return new ClientSetCompressionPacket(reader.readVarInt());
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeVarInt(this.threshold);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.LOGIN;
    }

    @Override
    public int id() {
        return 0x03;
    }
}
