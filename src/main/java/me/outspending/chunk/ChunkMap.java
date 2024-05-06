package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkLoadEvent;
import me.outspending.events.event.ChunkUnloadEvent;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public @NotNull Chunk loadChunk(int x, int z) {
        final int index = getChunkIndex(x, z);
        final Chunk chunk = Chunk.create(x, z, world, ChunkSection.generateChunkSections());

        storedChunks.put(index, chunk);
        EventExecutor.emitEvent(new ChunkLoadEvent(chunk));

        return chunk;
    }

    public void unloadChunk(int x, int z) {
        int chunkIndex = getChunkIndex(x, z);
        if (storedChunks.containsKey(chunkIndex)) {
            final Chunk removedChunk = storedChunks.remove(chunkIndex);
            EventExecutor.emitEvent(new ChunkUnloadEvent(removedChunk));
        }
    }

    public List<Chunk> getAllChunks() {
        return List.copyOf(storedChunks.values());
    }

}
