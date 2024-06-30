package me.outspending.generation;

import me.outspending.block.BlockType;
import me.outspending.position.Pos;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface BlockSetter {

    void setBlock(int x, int y, int z, @NotNull BlockType blockType);

    default void setBlock(@NotNull Pos pos, @NotNull BlockType blockType) {
        this.setBlock((int) pos.x(), (int) pos.y(), (int) pos.z(), blockType);
    }

}
