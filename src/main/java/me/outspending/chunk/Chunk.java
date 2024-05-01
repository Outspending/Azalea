package me.outspending.chunk;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface Chunk {
    void setBlock(int x, int y, int z, int blockID);
    int getBlock(int x, int y, int z);

    ChunkSection getSection(int y);
    ChunkSection[] getSections();

    CompletableFuture<Chunk> load();
    void unload();

    int getChunkX();
    int getChunkZ();
    boolean isLoaded();

    default void write(@NotNull PacketWriter writer) {
        for (ChunkSection chunkSection : getSections()) {
            chunkSection.write(writer);
        }
    }
}
