package me.outspending.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Property(@NotNull String name, @NotNull String value, @Nullable String signature) {
    public boolean isSigned() {
        return signature != null;
    }
}