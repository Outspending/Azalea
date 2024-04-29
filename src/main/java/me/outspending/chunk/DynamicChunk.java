package me.outspending.chunk;

import java.util.concurrent.CompletableFuture;

public class DynamicChunk extends AbstractChunk {
    DynamicChunk(int chunkX, int chunkZ, ChunkSection[] sections) {
        super(chunkX, chunkZ, sections);
    }

    @Override
    public ChunkSection getSection(int y) {
        return null;
    }

    @Override
    public CompletableFuture<Chunk> load() {
        return chunkMap.loadChunk(chunkX, chunkZ);
    }

    @Override
    public void unload() {

    }
}
