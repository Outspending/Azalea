package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetHeldItemPacket(short slot) implements ServerPacket {

    public static SetHeldItemPacket read(@NotNull PacketReader reader) {
        return new SetHeldItemPacket(reader.readShort());
    }

    @Override
    public int id() {
        return 0x2C;
    }

}
