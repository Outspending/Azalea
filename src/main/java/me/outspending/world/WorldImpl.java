package me.outspending.world;

import lombok.Getter;
import me.outspending.MinecraftServer;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkMap;
import me.outspending.entity.Entity;
import me.outspending.player.Player;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Pos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Getter
public class WorldImpl implements World {

    private final ChunkMap chunkMap = new ChunkMap(this);
    private final List<Entity> entities = Collections.synchronizedList(new ArrayList<>());
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());

    private final String name;
    private final WorldGenerator generator;

    public WorldImpl(String name, WorldGenerator generator) {
        this.name = name;
        this.generator = generator;

        MinecraftServer.getInstance().getServerProcess().getWorldCache().add(this);
    }

    public WorldImpl(String name) {
        this(name, new WorldGenerator());
    }

    @Override
    public @NotNull List<Entity> getAllEntities() {
        return entities;
    }

    @Override
    public @NotNull WorldGenerator getGenerator() {
        return generator;
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        if (entity instanceof Player player) {
            players.add(player);
        } else {
            entities.add(entity);
        }
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        if (entity instanceof Player player) {
            players.remove(player);
        } else {
            entities.remove(entity);
        }
    }

    @Override
    public @NotNull List<Chunk> getLoadedChunks() {
        return chunkMap.getAllChunks();
    }

    @Override
    public @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Pos centerPosition, int chunkDistance) {
        return chunkMap.getChunksRange(centerPosition, chunkDistance);
    }

    @Override
    public @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Pos centerPosition, int chunkDistance, Predicate<Chunk> predicate) {
        return chunkMap.getChunksRange(centerPosition, chunkDistance, predicate);
    }

    @Override
    public @NotNull Chunk getChunk(int x, int z) {
        return chunkMap.getChunk(x, z);
    }

    @Override
    public void tick(long time) {
        for (Entity entity : entities) {
            if (entity.isCanTick()) {
                entity.tick(time);
            }
        }

        for (Player player : players) {
            player.tick(time);
        }
    }

}
