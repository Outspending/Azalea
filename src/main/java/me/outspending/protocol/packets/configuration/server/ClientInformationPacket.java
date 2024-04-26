package me.outspending.protocol.packets.configuration.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientInformationPacket(
        String locale,
        byte viewDistance,
        int chatMode,
        boolean chatColors,
        byte skinParts,
        int mainHand,
        boolean textFiltering,
        boolean serverListings
) implements Packet {
    public static ClientInformationPacket of(@NotNull PacketReader reader) {
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
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(locale);
        writer.write(viewDistance);
        writer.writeVarInt(chatMode);
        writer.writeBoolean(chatColors);
        writer.write(skinParts);
        writer.writeVarInt(mainHand);
        writer.writeBoolean(textFiltering);
        writer.writeBoolean(serverListings);
    }

    @Override
    public int getID() {
        return 0x00;
    }
}
