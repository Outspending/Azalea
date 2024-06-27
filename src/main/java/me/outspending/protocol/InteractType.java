package me.outspending.protocol;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public enum InteractType {
    INTERACT,
    ATTACK,
    INTERACT_AT;

    public static @NotNull InteractType getById(@Range(from = 0, to = 3) int id) {
        final InteractType value = values()[id];
        return value != null ? value : INTERACT;
    }

}
