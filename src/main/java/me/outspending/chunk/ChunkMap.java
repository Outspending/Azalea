package me.outspending.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ChunkMap {
    private static final Long2ObjectOpenHashMap<CompletableFuture<Chunk>> CHUNKS = new Long2ObjectOpenHashMap<>();
    private static final ChunkLoader loader = new DynamicChunkLoader();

    public static ChunkGenerator generator = new TestingGenerator();

    public @Nullable CompletableFuture<Chunk> getLoadedChunk(int x, int z) {
        return getChunk(x, z, false);
    }

    public @Nullable CompletableFuture<Chunk> getChunk(int x, int z, boolean loadChunk) {
        long index = getChunkIndex(x, z);
        CompletableFuture<Chunk> chunk = CHUNKS.get(index);
        if (chunk == null && loadChunk) {
            chunk = loader.loadChunk(x, z);
            CHUNKS.put(index, chunk);

            generator.onChunkLoad(chunk);
        }

        return chunk;
    }

    public CompletableFuture<Chunk> loadChunk(int x, int z) {
        return getChunk(x, z, true);
    }

    public void unloadChunk(int x, int z) {
        CompletableFuture<Chunk> removedChunk = CHUNKS.remove(getChunkIndex(x, z));
        if (removedChunk != null) {
            loader.unloadChunk(x, z);
            generator.onChunkUnload(removedChunk);
        }
    }

    private long getChunkIndex(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }
}
