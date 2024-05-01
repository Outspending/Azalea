package me.outspending.chunk;

import me.outspending.chunk.palette.IndirectPalette;
import me.outspending.chunk.palette.SingleValuePalette;
import me.outspending.utils.MathUtils;

import java.util.concurrent.CompletableFuture;

public interface ChunkLoader {
    byte BITS_PER_BLOCK = (byte) Math.ceil(MathUtils.log2(384 + 1));

    default ChunkSection[] generateChunkSections() {
        ChunkSection[] sections = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            sections[i] = new ChunkSection(new IndirectPalette((byte) 8, 4098), new SingleValuePalette((byte) 3, 0));
        }

        return sections;
    }

    CompletableFuture<Chunk> loadChunk(int x, int z);
    void unloadChunk(int x, int z);
}
