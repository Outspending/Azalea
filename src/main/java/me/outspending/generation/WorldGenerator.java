package me.outspending.generation;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.outspending.chunk.Chunk;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Consumer;

@Getter
public class WorldGenerator {
    public static final WorldGenerator EMPTY = new WorldGenerator();

    private final Consumer<ChunkGenerator> consumer;

    public static WorldGenerator create(@NotNull Consumer<@NotNull ChunkGenerator> consumer) {
        return new WorldGenerator(consumer);
    }

    private WorldGenerator(@NotNull Consumer<@NotNull ChunkGenerator> consumer) {
        this.consumer = consumer;
    }

    private WorldGenerator() {
        this.consumer = chunkGenerator -> {};
    }

    @Contract("null -> fail")
    public void generate(@UnknownNullability Chunk chunk) {
        Preconditions.checkNotNull(chunk, "Chunk cannot be null!");
        this.consumer.accept(new NormalChunkGenerator(chunk));
    }

    @Contract("null -> fail")
    public @NotNull ChunkGenerator getChunkGenerator(@UnknownNullability Chunk chunk) {
        Preconditions.checkNotNull(chunk, "Chunk cannot be null!");
        return new NormalChunkGenerator(chunk);
    }

}
