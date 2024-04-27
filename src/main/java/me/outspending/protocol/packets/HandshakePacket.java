package me.outspending.protocol.packets;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class HandshakePacket extends ServerPacket {
    private final int protocolVersion;
    private final String serverAddress;
    private final short serverPort;
    private final int nextState;

    public static @NotNull HandshakePacket of(@NotNull PacketReader reader) {
        return new HandshakePacket(
                reader.readVarInt(),
                reader.readString(),
                reader.readShort(),
                reader.readVarInt()
        );
    }

    public HandshakePacket(int protocolVersion, String serverAddress, short serverPort, int state) {
        super(0x00);

        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = state;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(protocolVersion);
        writer.writeString(serverAddress);
        writer.writeShort(serverPort);
        writer.writeVarInt(nextState);
    }
}
