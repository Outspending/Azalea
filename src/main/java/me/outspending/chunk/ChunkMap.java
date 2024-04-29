package me.outspending.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ChunkMap {
    private static final Long2ObjectOpenHashMap<CompletableFuture<Chunk>> CHUNKS = new Long2ObjectOpenHashMap<>();
    private static final ChunkLoader loader = new DynamicChunkLoader();

    public @Nullable CompletableFuture<Chunk> getLoadedChunk(int x, int z) {
        return CHUNKS.get(getChunkIndex(x, z));
    }

    public @NotNull CompletableFuture<Chunk> getChunk(int x, int z) {
        long index = getChunkIndex(x, z);
        CompletableFuture<Chunk> chunk = CHUNKS.get(index);
        if (chunk == null) {
            chunk = loader.loadChunk(x, z);
            CHUNKS.put(index, chunk);
        }

        return chunk;
    }

    public CompletableFuture<Chunk> loadChunk(int x, int z) {
        return getChunk(x, z);
    }

    public void unloadChunk(int x, int z) {
        CHUNKS.remove(getChunkIndex(x, z));
        loader.unloadChunk(x, z);
    }

    private long getChunkIndex(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }
}
