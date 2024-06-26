package me.outspending.world;

import me.outspending.Tickable;
import me.outspending.chunk.Chunk;
import me.outspending.entity.Entity;
import me.outspending.generation.BlockGetter;
import me.outspending.generation.BlockSetter;
import me.outspending.player.Player;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Pos;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public interface World extends Tickable, BlockGetter, BlockSetter {

    static @NotNull World create(@NotNull String name) {
        return new WorldImpl(name);
    }

    static @NotNull Builder builder(@NotNull String name) {
        return new Builder(name);
    }

    @NotNull List<Entity> getAllEntities();

    default @NotNull List<Entity> getAllEntities(@NotNull Predicate<Entity> predicate) {
        return getAllEntities().stream()
                .filter(predicate)
                .toList();
    }

    @NotNull String getName();

    @NotNull List<Player> getPlayers();

    @NotNull WorldGenerator getGenerator();

    void addEntity(@NotNull Entity entity);

    void removeEntity(@NotNull Entity entity);

    boolean loadChunk(int x, int z, boolean generate);

    boolean unloadChunk(@NotNull Chunk chunk);

    @NotNull List<Chunk> getLoadedChunks();

    @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Chunk centerChunk, int chunkDistance);

    @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Chunk centerChunk, int chunkDistance, Predicate<Chunk> predicate);

    @Nullable Chunk getChunk(int x, int z, boolean loadIfNotExists);

    default @Nullable Chunk getChunk(int x, int z) {
        return this.getChunk(x, z, true);
    }

    default @Nullable Chunk getChunk(@NotNull Pos position) {
        return getChunk((int) position.x() >> 4, (int) position.z() >> 4);
    }

    class Builder {
        private final String name;

        private WorldGenerator generator = WorldGenerator.EMPTY;

        public Builder(String name) {
            this.name = name;
        }

        @Contract("_ -> new")
        public @NotNull Builder generator(@NotNull WorldGenerator generator) {
            this.generator = generator;
            return this;
        }

        public @NotNull World build() {
            return new WorldImpl(this.name, this.generator);
        }

    }

}
