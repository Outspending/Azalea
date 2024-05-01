package me.outspending.chunk;

import org.jetbrains.annotations.Contract;

import java.util.concurrent.CompletableFuture;

public interface ChunkGenerator {
    ChunkGenerator EMPTY_GENERATOR = new ChunkGenerator() {
        @Override
        public void onChunkLoad(CompletableFuture<Chunk> chunk) {}

        @Override
        public void onChunkUnload(CompletableFuture<Chunk> chunk) {}
    };

    @Contract("null -> fail")
    void onChunkLoad(CompletableFuture<Chunk> chunk);

    @Contract("null -> fail")
    void onChunkUnload(CompletableFuture<Chunk> chunk);
}
