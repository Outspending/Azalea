package me.outspending.chunk;

import me.outspending.chunk.palette.IndirectPalette;

import java.util.concurrent.CompletableFuture;

public interface ChunkLoader {
    default ChunkSection[] generateChunkSections() {
        ChunkSection[] sections = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            sections[i] = new ChunkSection(new IndirectPalette((byte) 8), new IndirectPalette((byte) 3));
        }

        return sections;
    }

    CompletableFuture<Chunk> loadChunk(int x, int z);
    void unloadChunk(int x, int z);
}
