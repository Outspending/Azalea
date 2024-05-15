package me.outspending.entity;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NonTickingEntity extends Entity {

    public NonTickingEntity(@NotNull EntityType type) {
        super(type);
    }

    public NonTickingEntity(@NotNull EntityType type, @NotNull UUID entityUUID) {
        super(type, entityUUID);
    }

}
