package me.outspending.entity;

import me.outspending.Tickable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class TickingEntity extends Entity implements Tickable {

    public TickingEntity(@NotNull EntityType type) {
        super(type);
    }

    public TickingEntity(@NotNull EntityType type, @NotNull UUID entityUUID) {
        super(type, entityUUID);
    }

}
