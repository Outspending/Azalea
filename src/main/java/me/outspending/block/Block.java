package me.outspending.block;

import lombok.Getter;
import me.outspending.position.Location;

@Getter
public class Block {
    private final Location location;
    private final BlockType type;

    public Block(Location location, BlockType type) {
        this.location = location;
        this.type = type;
    }
}
