package me.outspending.protocol.packets;

import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class HandshakePacket extends ServerPacket {
    private final int protocolVersion;
    private final String serverAddress;
    private final short serverPort;
    private final GameState state;

    public static @NotNull HandshakePacket of(@NotNull PacketReader reader) {
        return new HandshakePacket(
                reader.readVarInt(),
                reader.readString(),
                reader.readShort(),
                reader.readEnum(GameState.class)
        );
    }

    public HandshakePacket(int protocolVersion, String serverAddress, short serverPort, GameState state) {
        super(0x00);

        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.state = state;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(protocolVersion);
        writer.writeString(serverAddress);
        writer.writeShort(serverPort);
        writer.writeEnum(state);
    }
}
