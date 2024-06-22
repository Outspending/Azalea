package me.outspending.registry;

import me.outspending.NamespacedID;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface RegistryType {

    @NotNull NamespacedID getRegistryID();

    @NotNull CompoundBinaryTag toNBT();

}
