package me.outspending.generation;

import me.outspending.block.Material;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockSetter {
    void setBlock(int x, int y, int z, @NotNull Material material);
}
