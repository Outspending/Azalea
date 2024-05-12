package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientKeepAlivePacket(long keepAliveID) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(keepAliveID);
    }

    @Override
    public int id() {
        return 0x24;
    }

}
