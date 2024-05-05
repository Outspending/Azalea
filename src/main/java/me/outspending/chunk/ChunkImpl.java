package me.outspending.chunk;

import lombok.Getter;
import me.outspending.entity.Entity;
import me.outspending.position.Pos;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ChunkImpl(int chunkX, int chunkZ, World world, ChunkSection[] sections) implements Chunk {

    public ChunkImpl(int chunkX, int chunkZ, World world) {
        this(chunkX, chunkZ, world, ChunkSection.generateChunkSections());
    }

    @Override
    public void setBlock(int x, int y, int z, int blockID) {
        final ChunkSection section = getSectionAt(y);
        if (section != null) {
            section.setBlock(x, y, z, blockID);
        }
    }

    @Override
    public int getBlock(int x, int y, int z) {
        final ChunkSection section = getSectionAt(y);
        return section != null ? (int) section.getBlock(x, y, z) : 0;
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
    public @NotNull List<Entity> getEntities() {
        return world.getAllEntities(entity -> {
            Pos position = entity.getPosition();
            int entityChunkX = (int) position.x() >> 4;
            int entityChunkZ = (int) position.z() >> 4;

            return entityChunkX == chunkX && entityChunkZ == chunkZ;
        });
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
