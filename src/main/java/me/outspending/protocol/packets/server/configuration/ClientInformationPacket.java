package me.outspending.protocol.packets.server.configuration;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientInformationPacket extends ServerPacket {
    private final String locale;
    private final byte viewDistance;
    private final int chatMode;
    private final boolean chatColors;
    private final byte skinParts;
    private final int mainHand;
    private final boolean textFiltering;
    private final boolean serverListings;

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

    public ClientInformationPacket(String locale, byte viewDistance, int chatMode, boolean chatColors, byte skinParts, int mainHand, boolean textFiltering, boolean serverListings) {
        super(0x00);
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.skinParts = skinParts;
        this.mainHand = mainHand;
        this.textFiltering = textFiltering;
        this.serverListings = serverListings;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(locale);
        writer.writeByte(viewDistance);
        writer.writeVarInt(chatMode);
        writer.writeBoolean(chatColors);
        writer.writeByte(skinParts);
        writer.writeVarInt(mainHand);
        writer.writeBoolean(textFiltering);
        writer.writeBoolean(serverListings);
    }
}
