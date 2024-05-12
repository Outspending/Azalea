package me.outspending.generation;

import me.outspending.block.Material;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockGetter {
   @NotNull Material getBlock(int x, int y, int z);
}
