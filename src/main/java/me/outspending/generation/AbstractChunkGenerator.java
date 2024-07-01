package me.outspending.generation;

import lombok.Getter;
import me.outspending.block.BlockType;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@Getter
public abstract class AbstractChunkGenerator implements ChunkGenerator {
    private final Chunk chunk;

    public AbstractChunkGenerator(Chunk chunk) {
        this.chunk = chunk;
    }

    private ChunkSection[] getSectionsFrom(int minHeight, int maxHeight) {
        ChunkSection[] sections = new ChunkSection[maxHeight - minHeight];
        for (int i = minHeight; i < maxHeight; i++) {
            sections[i] = chunk.getChunkSections()[i];
        }

        return sections;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        chunk.setBlock(x, y, z, blockType);
    }

    @Override
    public void fill(@NotNull BlockType blockType) {
        ChunkSection[] sections = chunk.getChunkSections();
        for (ChunkSection section : sections) {
            fillSection(section, blockType);
        }
    }

    @Override
    public void fillSection(@Range(from = 0, to = 24) int sectionY, @NotNull BlockType blockType) {
        ChunkSection section = chunk.getChunkSections()[sectionY];
        fillSection(section, blockType);
    }

    @Override
    public void fillSection(ChunkSection section, @NotNull BlockType blockType) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    section.setBlock(x, y, z, blockType);
                }
            }
        }
    }

    @Override
    public void fillHeight(int height, @NotNull BlockType blockType) {
        ChunkSection[] sections = getSectionsFrom(-64, height);
        int leftOver = height % 16;
        int maxIndex = sections.length;
        for (int i = 0; i < maxIndex; i++) {
            ChunkSection section = sections[i];
            if (i != maxIndex) {
                fillSection(section, blockType);
            } else {
                for (int j = 0; j < leftOver; j++) {
                    fillY(section, j, blockType);
                }
            }
        }
    }

    @Override
    public void fillHeight(int minHeight, int maxHeight, @NotNull BlockType blockType) {

    }

    @Override
    public void fillY(ChunkSection section, int y, @NotNull BlockType blockType) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                section.setBlock(x, y, z, blockType);
            }
        }
    }

    @Override
    public void fillY(int y, @NotNull BlockType blockType) {
        ChunkSection section = chunk.getChunkSections()[y >> 4];
        fillY(section, y, blockType);
    }

}
