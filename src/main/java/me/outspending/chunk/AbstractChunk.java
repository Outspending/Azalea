package me.outspending.chunk;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter @Setter(AccessLevel.MODULE)
public abstract class AbstractChunk implements Chunk {
    public static final ChunkMap chunkMap = new ChunkMap(); // TODO: Very bad practice, fix this later. Mostly for testing

    protected final ChunkSection[] sections;
    protected final int chunkX;
    protected final int chunkZ;

    protected boolean isLoaded = false;

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

    public boolean isLoaded() {
        return isLoaded;
    }
}
