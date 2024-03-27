package me.outspending.protocol.packets.handshaking;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record HandshakePacket(int protocolVersion, String serverAddress, short serverPort,
                              int nextState) implements Packet {

    public static @NotNull HandshakePacket of(@NotNull PacketReader reader) {
        return of(
                reader.readVarInt(),
                reader.readString(),
                reader.readShort(),
                reader.readVarInt()
        );
    }

    public static @NotNull HandshakePacket of(int protocolVersion, String serverAddress, short serverPort, int nextState) {
        return new HandshakePacket(protocolVersion, serverAddress, serverPort, nextState);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(protocolVersion);
        writer.writeString(serverAddress);
        writer.writeShort(serverPort);
        writer.writeVarInt(nextState);
    }

    @Override
    public int getID() {
        return 0x00;
    }
}
