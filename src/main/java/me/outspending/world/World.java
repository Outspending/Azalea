package me.outspending.world;

import me.outspending.Tickable;
import me.outspending.chunk.Chunk;
import me.outspending.entity.Entity;
import me.outspending.entity.Player;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Pos;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public interface World extends Tickable {
    static @NotNull World create(@NotNull String name) {
        return new WorldImpl(name);
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

    @NotNull List<Chunk> getLoadedChunks();

    @NotNull List<Chunk> getChunksInRange(@NotNull Pos centerPosition, int chunkDistance);

    @NotNull List<Chunk> getChunksInRange(@NotNull Pos centerPosition, int chunkDistance, Predicate<Chunk> predicate);

    @NotNull Chunk getChunk(int x, int z);

    default @NotNull Chunk getChunk(@NotNull Pos position) {
        return getChunk((int) position.x() >> 4, (int) position.z() >> 4);
    }
}
