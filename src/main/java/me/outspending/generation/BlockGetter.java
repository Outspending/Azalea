package me.outspending.generation;

import me.outspending.block.Block;
import me.outspending.block.BlockType;
import me.outspending.position.Pos;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockGetter {

   @NotNull BlockType getBlock(int x, int y, int z);

   default @NotNull BlockType getBlock(@NotNull Pos pos) {
        return this.getBlock((int) pos.x(), (int) pos.y(), (int) pos.z());
   }

}
