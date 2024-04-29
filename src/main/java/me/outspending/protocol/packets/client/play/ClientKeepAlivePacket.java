package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientKeepAlivePacket(long keepAliveID) implements ClientPacket {
    public static ClientKeepAlivePacket of(@NotNull PacketReader reader) {
        return new ClientKeepAlivePacket(reader.readLong());
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLong(keepAliveID);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x24;
    }
}
