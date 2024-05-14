package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ChunkBatchReceivedPacket(ClientConnection connection, float chunksPerTick) implements ServerPacket {
    public static ChunkBatchReceivedPacket read(ClientConnection connection, @NotNull PacketReader reader) {
        return new ChunkBatchReceivedPacket(connection, reader.readFloat());
    }

    @Override
    public int id() {
        return 0x07;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
