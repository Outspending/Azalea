package me.outspending.generation;

import me.outspending.block.BlockType;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockGetter {
   @NotNull
   BlockType getBlock(int x, int y, int z);
}
