package me.outspending.generation;

import me.outspending.block.BlockType;
import me.outspending.chunk.ChunkSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface ChunkGenerator extends BlockSetter {

    void fill(@NotNull BlockType blockType);

    void fillSection(@Range(from = 0, to = 24) int sectionY, @NotNull BlockType blockType);

    void fillSection(ChunkSection section, @NotNull BlockType blockType);

    void fillHeight(int height, @NotNull BlockType blockType);

    void fillHeight(int minHeight, int maxHeight, @NotNull BlockType blockType);

    void fillY(ChunkSection section, int y, @NotNull BlockType blockType);

    void fillY(int y, @NotNull BlockType blockType);

}
