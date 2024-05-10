package me.outspending.generation;

import lombok.Getter;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkSection;
import org.jetbrains.annotations.Range;

@Getter
public class ChunkGenerator implements Generator {
    private final Chunk chunk;

    ChunkGenerator(Chunk chunk) {
        this.chunk = chunk;
    }

    public void fill(int blockID) {
        ChunkSection[] sections = chunk.getSections();
        for (ChunkSection section : sections) {
            fillSection(section, blockID);
        }
    }

    public void fillSection(@Range(from = 0, to = 24) int sectionY, int blockID) {
        ChunkSection section = chunk.getSections()[sectionY];
        fillSection(section, blockID);
    }

    public void fillSection(ChunkSection section, int blockID) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    section.setBlock(x, y, z, blockID);
                }
            }
        }
    }

    private ChunkSection[] getSectionsFrom(int minHeight, int maxHeight) {
        ChunkSection[] sections = new ChunkSection[maxHeight - minHeight];
        for (int i = minHeight; i < maxHeight; i++) {
            sections[i] = chunk.getSections()[i];
        }

        return sections;
    }

    public void fillHeight(int height, int blockID) {
        ChunkSection[] sections = getSectionsFrom(-64, height);
        int leftOver = height % 16;
        int maxIndex = sections.length;
        for (int i = 0; i < maxIndex; i++) {
            ChunkSection section = sections[i];
            if (i != maxIndex) {
                fillSection(section, blockID);
            } else {
                for (int j = 0; j < leftOver; j++) {
                    fillY(section, j, blockID);
                }
            }
        }
    }

    public void fillHeight(int minHeight, int maxHeight, int blockID) {

    }

    public void fillY(ChunkSection section, int y, int blockID) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                section.setBlock(x, y, z, blockID);
            }
        }
    }

    public void fillY(int y, int blockID) {
        ChunkSection section = chunk.getSections()[y >> 4];
        fillY(section, y, blockID);
    }

}
