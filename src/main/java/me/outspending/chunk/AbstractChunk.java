package me.outspending.chunk;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter @Setter(AccessLevel.MODULE)
public abstract class AbstractChunk implements Chunk {
    protected static final ChunkMap chunkMap = new ChunkMap();

    protected final ChunkSection[] sections;
    protected final int chunkX;
    protected final int chunkZ;

    protected boolean isLoaded = false;

    protected AbstractChunk(int chunkX, int chunkZ) {
        this.sections = ChunkSection.createSections();
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    protected AbstractChunk(int chunkX, int chunkZ, ChunkSection[] sections) {
        this.sections = sections;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public @Nullable ChunkSection getSection(int y) {
        return sections[y >> 4];
    }

    @Override
    public int getBlock(int x, int y, int z) {
        ChunkSection section = getSection(y);
        if (section == null) return 0;

        return section.getBlock(x, y, z);
    }

    @Override
    public void setBlock(int x, int y, int z, int blockID) {
        ChunkSection section = getSection(y);
        if (section == null) return;

        section.setBlock(x, y, z, blockID);
    }

    protected void write(@NotNull PacketWriter writer) {
        for (ChunkSection chunkSection : sections) {
            chunkSection.write(writer);
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
