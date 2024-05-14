package me.outspending.generation;

import lombok.Getter;
import me.outspending.chunk.Chunk;

import java.util.function.Consumer;

@Getter
public class WorldGenerator {

    public void generate(Chunk chunk, Consumer<ChunkGenerator> consumer) {
        consumer.accept(new ConcurrentChunkGenerator(chunk));
    }

    public ChunkGenerator getChunkGenerator(Chunk chunk) {
        return new ConcurrentChunkGenerator(chunk) {
        };
    }

}
