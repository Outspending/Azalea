package me.outspending.block;

import lombok.Getter;
import me.outspending.position.Location;

public record Block(Location location, BlockType type) {}
