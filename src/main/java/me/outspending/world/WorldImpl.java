package me.outspending.world;

import lombok.Getter;
import me.outspending.MinecraftServer;
import me.outspending.block.BlockType;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkMap;
import me.outspending.entity.Entity;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.EntityWorldAddEvent;
import me.outspending.events.event.EntityWorldRemoveEvent;
import me.outspending.player.Player;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.ClientBlockUpdatePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Getter
public final class WorldImpl implements World {

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
        this(name, WorldGenerator.EMPTY);
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
        }
        entities.add(entity);

        EventExecutor.emitEvent(new EntityWorldAddEvent(entity, this, entity.getPosition()));
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        if (entity instanceof Player player) {
            players.remove(player);
        }
        entities.remove(entity);

        EventExecutor.emitEvent(new EntityWorldRemoveEvent(entity, this, entity.getPosition()));
    }

    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        return chunkMap.loadChunk(x, z, generate) != null;
    }

    @Override
    public boolean unloadChunk(@NotNull Chunk chunk) {
        return chunkMap.unloadChunk(chunk.getChunkX(), chunk.getChunkZ()) != null;
    }

    @Override
    public @NotNull List<Chunk> getLoadedChunks() {
        return chunkMap.getAllChunks();
    }

    @Override
    public @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Chunk centerChunk, int chunkDistance) {
        return chunkMap.getChunksRange(centerChunk, chunkDistance);
    }

    @Override
    public @NotNull CompletableFuture<List<Chunk>> getChunksInRange(@NotNull Chunk centerChunk, int chunkDistance, Predicate<Chunk> predicate) {
        return chunkMap.getChunksRange(centerChunk, chunkDistance, predicate);
    }

    @Override
    public @Nullable Chunk getChunk(int x, int z, boolean loadIfNotExists) {
        return chunkMap.getChunk(x, z, loadIfNotExists);
    }

    @Override
    public void tick(long time) {
        entities.stream()
                .filter(entity -> entity.getEntityMeta().isCanTick())
                .forEach(entity -> entity.tick(time));

        players.forEach(player -> player.tick(time));
    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        final Chunk chunkAt = chunkMap.getChunk(x >> 4, z >> 4, false);
        if (chunkAt == null) {
            return BlockType.AIR;
        }

        return chunkAt.getBlock(x, y, z);
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        final Chunk chunkAt = chunkMap.getChunk(x, z, false);
        if (chunkAt == null) {
            return;
        }

        chunkAt.setBlock(x >> 4, y >> 4, z >> 4, blockType);
        for (Player player : chunkAt.getAllPlayersSeeingChunk()) {
            player.sendPacket(new ClientBlockUpdatePacket(x, y, z, blockType));
        }
    }

}
