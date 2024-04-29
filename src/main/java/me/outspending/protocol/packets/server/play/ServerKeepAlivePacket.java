package me.outspending.protocol.packets.server.play;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ServerKeepAlivePacket(long keepAliveID) implements ServerPacket {
    public static ServerKeepAlivePacket read(PacketReader reader) {
        return new ServerKeepAlivePacket(reader.readLong());
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x15;
    }
}
