package me.outspending.chunk;

import java.util.concurrent.CompletableFuture;

public class TestingGenerator implements ChunkGenerator {
    @Override
    public void onChunkLoad(CompletableFuture<Chunk> chunk) {
        chunk.thenAccept(chunk1 -> {
            ChunkSection section = chunk1.getSection(40);
            section.fill(1);
        });
    }

    @Override
    public void onChunkUnload(CompletableFuture<Chunk> chunk) {

    }
}
