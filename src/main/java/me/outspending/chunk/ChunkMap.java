package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkLoadEvent;
import me.outspending.events.event.ChunkUnloadEvent;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public final class ChunkMap {
    private final Int2ObjectOpenHashMap<Chunk> storedChunks = new Int2ObjectOpenHashMap<>();
    private final World world;

    public ChunkMap(@NotNull World world) {
        this.world = world;
    }

    private int hashChunkCoords(int x, int z) {
        int hash = x;
        hash = 31 * hash + z;
        return hash;
    }

    public @Nullable Chunk getChunk(int x, int z, boolean loadIfNotExists) {
        int index = hashChunkCoords(x, z);
        return storedChunks.containsKey(index) ? storedChunks.get(index) : loadIfNotExists ? loadChunk(x, z, true) : null;
    }

    public @NotNull CompletableFuture<List<Chunk>> getChunksRange(@NotNull Chunk centerChunk, int distanceChunks) {
        return CompletableFuture.supplyAsync(() -> {
            List<Chunk> chunks = new ArrayList<>();
            int chunkX = centerChunk.getChunkX();
            int chunkZ = centerChunk.getChunkZ();

            if (distanceChunks == 0) {
                chunks.add(getChunk(chunkX, chunkZ, true));
            } else {
                for (int dx = -distanceChunks; dx <= distanceChunks; dx++) {
                    for (int dz = -distanceChunks; dz <= distanceChunks; dz++) {
                        final int newChunkX = chunkX + dx;
                        final int newChunkZ = chunkZ + dz;

                        Chunk chunk = this.getChunk(newChunkX, newChunkZ, true);

                        chunks.add(chunk);
                    }
                }
            }

            return chunks;
        });
    }

    public @NotNull CompletableFuture<List<Chunk>> getChunksRange(@NotNull Chunk centerChunk, int distanceChunks, Predicate<Chunk> chunkPredicate) {
        return getChunksRange(centerChunk, distanceChunks).thenApply(chunks -> chunks.stream()
                .filter(chunkPredicate)
                .toList());
    }

    public boolean isChunkSaved(@NotNull Chunk chunk) {
        return storedChunks.containsKey(hashChunkCoords(chunk.getChunkX(), chunk.getChunkZ()));
    }

    public @Nullable Chunk loadChunk(int x, int z, boolean generate) {
        final int index = hashChunkCoords(x, z);
        if (storedChunks.containsKey(index)) {
            return null;
        }

        final Chunk chunk = Chunk.create(x, z, world);
        chunk.load(generate);

        storedChunks.put(index, chunk);
        EventExecutor.emitEvent(new ChunkLoadEvent(chunk));

        return chunk;
    }

    public @Nullable Chunk unloadChunk(int x, int z) {
        int chunkIndex = hashChunkCoords(x, z);
        if (!storedChunks.containsKey(chunkIndex)) {
            return null;
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
