package me.outspending.chunk;

import lombok.Getter;
import me.outspending.block.BlockType;
import me.outspending.entity.Entity;
import me.outspending.position.Pos;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public final class ChunkImpl implements Chunk {
    private boolean isLoaded = false;

    private final int chunkX;
    private final int chunkZ;
    private final World world;
    private final ChunkSection[] sections;

    public ChunkImpl(int chunkX, int chunkZ, World world, ChunkSection[] sections) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.world = world;
        this.sections = sections;
    }

    public ChunkImpl(int chunkX, int chunkZ, World world) {
        this(chunkX, chunkZ, world, ChunkSection.generateChunkSections());
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        final ChunkSection section = getSectionAt(y);
        if (section != null) {
            section.setBlock(x, y, z, blockType);
        }
    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        final ChunkSection section = getSectionAt(y);
        return section != null ? section.getBlock(x, y, z) : BlockType.AIR;
    }

    @Override
    public @NotNull ChunkSection[] getSections() {
        return sections;
    }

    @Override
    public @NotNull World getWorld() {
        return world;
    }

    @Override
    public int getChunkX() {
        return chunkX;
    }

    @Override
    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public @Nullable ChunkSection getSectionAt(int y) {
        return sections[y >> 4];
    }

    @Override
    public @NotNull ChunkSection[] getSectionsBelow(int y) {
        int delta = Math.max(0, y / 16);
        ChunkSection[] belowSections = new ChunkSection[delta];
        if (delta > 0) {
            System.arraycopy(sections, 0, belowSections, 0, delta);
        }
        return belowSections;
    }

    @Override
    public @NotNull ChunkSection[] getSectionsAbove(int y) {
        int delta = Math.max(0, y / 16);
        int aboveSize = Math.max(0, 16 - delta);
        ChunkSection[] aboveSections = new ChunkSection[aboveSize];
        if (aboveSize > 0) {
            System.arraycopy(sections, delta, aboveSections, 0, aboveSize);
        }
        return aboveSections;
    }

    @Override
    public @NotNull List<Entity> getEntities() {
        return world.getAllEntities(entity -> {
            Pos position = entity.getPosition();
            int entityChunkX = (int) position.x() >> 4;
            int entityChunkZ = (int) position.z() >> 4;

            return entityChunkX == chunkX && entityChunkZ == chunkZ;
        });
    }

    @Override
    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {
        // TODO: Implement
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        for (ChunkSection section : sections) {
            section.write(writer);
        }
    }
}
