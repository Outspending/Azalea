package me.outspending.generation;

import me.outspending.block.BlockType;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockSetter {
    void setBlock(int x, int y, int z, @NotNull BlockType blockType);
}
