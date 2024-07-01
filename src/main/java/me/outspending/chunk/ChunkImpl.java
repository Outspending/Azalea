package me.outspending.chunk;

import lombok.Getter;
import me.outspending.MinecraftServer;
import me.outspending.block.BlockType;
import me.outspending.entity.Entity;
import me.outspending.generation.ChunkGenerator;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
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
        final ChunkSection section = getChunkSection(y);
        if (section != null) {
            section.setBlock(x, y, z, blockType);
        }
    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        final ChunkSection section = getChunkSection(y);
        return section != null ? section.getBlock(x, y, z) : BlockType.AIR;
    }

    @Override
    public @NotNull ChunkSection[] getChunkSections() {
        return this.sections;
    }

    @Override
    public @Nullable ChunkSection getChunkSection(int sectionY) {
        if (this.sections.length <= sectionY) {
            return null;
        }

        return this.sections[sectionY];
    }

    @Override
    public @NotNull ChunkGenerator getGenerator() {
        return this.world.getGenerator().getChunkGenerator(this);
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
    public @NotNull List<Entity> getEntities() {
        return world.getAllEntities(entity -> {
            Pos position = entity.getPosition();
            int entityChunkX = (int) position.x() >> 4;
            int entityChunkZ = (int) position.z() >> 4;

            return entityChunkX == chunkX && entityChunkZ == chunkZ;
        });
    }

    @Override
    public boolean load(boolean generate) {
        if (this.isLoaded) {
            return false;
        } else {
            this.isLoaded = true;
            if (generate) {
                this.world.getGenerator().generate(this);
            }

            return this.isLoaded;
        }
    }

    @Override
    public void unload() {
        this.isLoaded = false;
    }

    @Override
    public @NotNull Collection<Player> getAllPlayersSeeingChunk() {
        Collection<Player> seeingPlayers = new HashSet<>();
        for (Player player : MinecraftServer.getInstance().getAllPlayers()) {
            if (player.canSeeChunk(this)) {
                seeingPlayers.add(player);
            }
        }

        return seeingPlayers;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        for (ChunkSection section : sections) {
            section.write(writer);
        }
    }

    @Override
    public String toString() {
        return "Chunk{x=" + chunkX + ", z=" + chunkZ + "}";
    }

}
