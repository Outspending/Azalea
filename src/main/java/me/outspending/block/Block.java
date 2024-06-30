package me.outspending.block;

import me.outspending.generation.BlockGetter;
import me.outspending.generation.BlockSetter;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;

public record Block(@NotNull World world, @NotNull Pos pos) {

    public @NotNull BlockType getType() {
        return world.getBlock(this.pos);
    }

    public void setType(@NotNull BlockType blockType) {
        world.setBlock(pos, blockType);
    }

}
