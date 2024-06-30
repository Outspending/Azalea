package me.outspending.protocol.packets.server.configuration;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ClientInformationPacket(
        @NotNull String locale,
        byte viewDistance,
        int chatMode,
        boolean chatColors,
        byte skinParts,
        int mainHand,
        boolean textFiltering,
        boolean serverListings
) implements ServerPacket {

    public static ClientInformationPacket read(@NotNull PacketReader reader) {
        return new ClientInformationPacket(
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

}
