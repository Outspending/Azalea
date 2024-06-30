package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SwingArmPacket(int hand) implements ServerPacket {

    public static SwingArmPacket read(@NotNull PacketReader reader) {
        return new SwingArmPacket(reader.readVarInt());
    }

    @Override
    public int id() {
        return 0x33;
    }

}
