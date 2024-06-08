package me.outspending.registry;

import me.outspending.NamespacedID;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RegistryType {
    @NotNull NamespacedID getRegistryID();
}
