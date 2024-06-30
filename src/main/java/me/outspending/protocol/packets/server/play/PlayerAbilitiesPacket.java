package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerAbilitiesPacket(byte flags) implements ServerPacket {

    public static PlayerAbilitiesPacket read(@NotNull PacketReader reader) {
        return new PlayerAbilitiesPacket(reader.readByte());
    }

    @Override
    public int id() {
        return 0x20;
    }

}
