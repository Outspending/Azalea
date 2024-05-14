package me.outspending.protocol.packets.server.configuration;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientInformationPacket(
        ClientConnection connection,
        String locale,
        byte viewDistance,
        int chatMode,
        boolean chatColors,
        byte skinParts,
        int mainHand,
        boolean textFiltering,
        boolean serverListings
) implements ServerPacket {
    public static ClientInformationPacket read(ClientConnection connection, PacketReader reader) {
        return new ClientInformationPacket(
                connection,
                reader.readString(),
                reader.readByte(),
                reader.readVarInt(),
                reader.readBoolean(),
                reader.readByte(),
                reader.readVarInt(),
                reader.readBoolean(),
                reader.readBoolean()
        );
    }

    @Override
    public int id() {
        return 0x00;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
