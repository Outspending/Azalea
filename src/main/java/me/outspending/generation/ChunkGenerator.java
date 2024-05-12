package me.outspending.generation;

import lombok.Getter;
import me.outspending.block.Material;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@Getter
public class ChunkGenerator implements Generator {
    private final Chunk chunk;

    ChunkGenerator(Chunk chunk) {
        this.chunk = chunk;
    }

    public void fill(@NotNull Material material) {
        ChunkSection[] sections = chunk.getSections();
        for (ChunkSection section : sections) {
            fillSection(section, material);
        }
    }

    public void fillSection(@Range(from = 0, to = 24) int sectionY, @NotNull Material material) {
        ChunkSection section = chunk.getSections()[sectionY];
        fillSection(section, material);
    }

    public void fillSection(ChunkSection section, @NotNull Material material) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    section.setBlock(x, y, z, material);
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

    public void fillHeight(int height, @NotNull Material material) {
        ChunkSection[] sections = getSectionsFrom(-64, height);
        int leftOver = height % 16;
        int maxIndex = sections.length;
        for (int i = 0; i < maxIndex; i++) {
            ChunkSection section = sections[i];
            if (i != maxIndex) {
                fillSection(section, material);
            } else {
                for (int j = 0; j < leftOver; j++) {
                    fillY(section, j, material);
                }
            }
        }
    }

    public void fillHeight(int minHeight, int maxHeight, @NotNull Material material) {

    }

    public void fillY(ChunkSection section, int y, @NotNull Material material) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                section.setBlock(x, y, z, material);
            }
        }
    }

    public void fillY(int y, @NotNull Material material) {
        ChunkSection section = chunk.getSections()[y >> 4];
        fillY(section, y, material);
    }

}
