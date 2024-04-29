package me.outspending.protocol.packets.server;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record HandshakePacket(int protocolVersion, String serverAddress, short serverPort, int nextState) implements ServerPacket {
    public static HandshakePacket read(PacketReader reader) {
        return new HandshakePacket(
                reader.readVarInt(),
                reader.readString(),
                reader.readShort(),
                reader.readVarInt()
        );
    }

    @Override
    public @NotNull GameState state() {
        return GameState.HANDSHAKE;
    }

    @Override
    public int id() {
        return 0x00;
    }
}
