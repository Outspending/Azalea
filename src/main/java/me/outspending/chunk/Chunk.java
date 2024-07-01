package me.outspending.chunk;

import me.outspending.entity.Entity;
import me.outspending.generation.BlockGetter;
import me.outspending.generation.BlockSetter;
import me.outspending.generation.ChunkGenerator;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.Writable;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface Chunk extends Writable, BlockGetter, BlockSetter {

    static @NotNull Chunk create(int chunkX, int chunkZ, @NotNull World world, @NotNull ChunkSection[] sections) {
        return new ChunkImpl(chunkX, chunkZ, world, sections);
    }

    static @NotNull Chunk create(int chunkX, int chunkZ, @NotNull World world) {
        return new ChunkImpl(chunkX, chunkZ, world);
    }

    default int getChunkIndex(int x, int z) {
        return (x << 4) | z;
    }

    default int getChunkIndex(@NotNull Chunk chunk) {
        return getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
    }

    @NotNull ChunkSection[] getChunkSections();

    @Nullable ChunkSection getChunkSection(int sectionY);

    default @Nullable ChunkSection getChunkSection(@NotNull Pos pos) {
        return this.getChunkSection((int) pos.y() >> 4);
    }

    @NotNull ChunkGenerator getGenerator();

    @NotNull World getWorld();

    int getChunkX();

    int getChunkZ();

    @NotNull List<Entity> getEntities();

    boolean isLoaded();

    boolean load(boolean generate);

    default boolean load() {
        return this.load(true);
    }

    void unload();

    @NotNull Collection<Player> getAllPlayersSeeingChunk();

}