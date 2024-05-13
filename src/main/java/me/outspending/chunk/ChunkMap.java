package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkLoadEvent;
import me.outspending.events.event.ChunkUnloadEvent;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public final class ChunkMap {
    private final Int2ObjectOpenHashMap<Chunk> storedChunks = new Int2ObjectOpenHashMap<>();
    private final World world;

    public ChunkMap(@NotNull World world) {
        this.world = world;
    }

    private int getChunkIndex(int x, int z) {
        return (x << 16) | (z & 0xFFFF);
    }

    public @NotNull Chunk getChunk(int x, int z) {
        int index = getChunkIndex(x, z);
        return storedChunks.containsKey(index) ? storedChunks.get(index) : loadChunk(x, z);
    }

    public @NotNull CompletableFuture<List<Chunk>> getChunksRange(@NotNull Pos centerPosition, int distanceChunks) {
        return CompletableFuture.supplyAsync(() -> {
            List<Chunk> chunks = new ArrayList<>();
            int chunkX = (int) centerPosition.x() >> 4;
            int chunkZ = (int) centerPosition.z() >> 4;

            if (distanceChunks == 0) {
                chunks.add(getChunk(chunkX, chunkZ));
            } else {
                for (int x = chunkX - distanceChunks; x <= chunkX + distanceChunks; x++) {
                    for (int z = chunkZ - distanceChunks; z <= chunkZ + distanceChunks; z++) {
                        Chunk chunk = getChunk(x, z);
                        chunks.add(chunk);
                    }
                }
            }

            return chunks;
        });
    }

    public @NotNull CompletableFuture<List<Chunk>> getChunksRange(@NotNull Pos centerPosition, int distanceChunks, Predicate<Chunk> chunkPredicate) {
        return getChunksRange(centerPosition, distanceChunks).thenApply(chunks -> chunks.stream()
                .filter(chunkPredicate)
                .toList());
    }

    public @NotNull Chunk loadChunk(int x, int z) {
        final int index = getChunkIndex(x, z);
        if (storedChunks.containsKey(index)) {
            return storedChunks.get(index);
        } else {
            final Chunk chunk = Chunk.create(x, z, world, ChunkSection.generateChunkSections());

            storedChunks.put(index, chunk);
            EventExecutor.emitEvent(new ChunkLoadEvent(chunk));

            return chunk;
        }
    }

    public @NotNull Chunk unloadChunk(int x, int z) {
        int chunkIndex = getChunkIndex(x, z);
        if (!storedChunks.containsKey(chunkIndex)) {
            return getChunk(x, z);
        } else {
            final Chunk removedChunk = storedChunks.remove(chunkIndex);
            EventExecutor.emitEvent(new ChunkUnloadEvent(removedChunk));

            return removedChunk;
        }
    }

    public List<Chunk> getAllChunks() {
        return List.copyOf(storedChunks.values());
    }

}
