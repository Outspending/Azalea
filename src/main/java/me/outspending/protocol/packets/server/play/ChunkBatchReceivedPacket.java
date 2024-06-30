package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ChunkBatchReceivedPacket(float chunksPerTick) implements ServerPacket {

    public static ChunkBatchReceivedPacket read(@NotNull PacketReader reader) {
        return new ChunkBatchReceivedPacket(reader.readFloat());
    }

    @Override
    public int id() {
        return 0x07;
    }

}
