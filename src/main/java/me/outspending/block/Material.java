package me.outspending.block;

import lombok.Getter;

@Getter
public enum Material {
    GRASS_BLOCK(2),
    STONE(1);

    private final int id;

    Material(int id) {
        this.id = id;
    }
}
