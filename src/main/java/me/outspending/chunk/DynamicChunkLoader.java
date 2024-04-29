package me.outspending.chunk;

import java.util.concurrent.CompletableFuture;

public class DynamicChunkLoader implements ChunkLoader {
    @Override
    public CompletableFuture<Chunk> loadChunk(int x, int z) {
        return CompletableFuture.supplyAsync(() -> {
            ChunkSection[] sections = generateChunkSections();
            return new DynamicChunk(x, z, sections);
        });
    }

    @Override
    public void unloadChunk(int x, int z) {

    }
}
