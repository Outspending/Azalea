package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.List;

public final class ChunkMap {
    private final Int2ObjectOpenHashMap<Chunk> storedChunks = new Int2ObjectOpenHashMap<>();

    private int getChunkIndex(int x, int z) {
        return (x << 4) | z;
    }

    public Chunk getChunk(int x, int z) {
        return storedChunks.get(getChunkIndex(x, z));
    }

    public void saveChunk(int x, int z, Chunk chunk) {
        int chunkIndex = getChunkIndex(x, z);
        if (!storedChunks.containsKey(chunkIndex)) {
            storedChunks.put(chunkIndex, chunk);
        }
    }

    public void unloadChunk(int x, int z) {
        int chunkIndex = getChunkIndex(x, z);
        if (storedChunks.containsKey(chunkIndex)) {
            storedChunks.remove(chunkIndex);
        }
    }

    public List<Chunk> getAllChunks() {
        return List.copyOf(storedChunks.values());
    }
}
