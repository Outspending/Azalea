package me.outspending.entity;

import lombok.Getter;

@Getter
public enum EntityType {
    PLAYER(124);

    private final int id;

    EntityType(int id) {
        this.id = id;
    }

}
