package me.outspending.chunk;

import java.util.concurrent.CompletableFuture;

public interface Chunk {
    void setBlock(int x, int y, int z, int blockID);
    int getBlock(int x, int y, int z);

    ChunkSection getSection(int y);
    ChunkSection[] getSections();

    CompletableFuture<Chunk> load();
    void unload();

    boolean isLoaded();
}
