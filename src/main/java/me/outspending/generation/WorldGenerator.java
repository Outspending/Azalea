package me.outspending.generation;

import lombok.Getter;
import me.outspending.chunk.Chunk;

import java.util.function.Consumer;

@Getter
public class WorldGenerator {
    public static final WorldGenerator EMPTY = new WorldGenerator();

    private final Consumer<ChunkGenerator> consumer;

    public static WorldGenerator create(Consumer<ChunkGenerator> consumer) {
        return new WorldGenerator(consumer);
    }

    private WorldGenerator(Consumer<ChunkGenerator> consumer) {
        this.consumer = consumer;
    }

    private WorldGenerator() {
        this.consumer = chunkGenerator -> {};
    }

    public void generate(Chunk chunk) {
        this.consumer.accept(new NormalChunkGenerator(chunk));
    }

    public ChunkGenerator getChunkGenerator(Chunk chunk) {
        return new NormalChunkGenerator(chunk);
    }

}
