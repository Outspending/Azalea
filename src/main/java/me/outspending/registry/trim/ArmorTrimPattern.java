package me.outspending.registry.trim;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ArmorTrimPattern(
        @NotNull NamespacedID registryID,
        @NotNull String assetName,
        @NotNull String templateItem,
        @NotNull String description,
        byte decal
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .putString("asset_name", this.assetName)
                .putString("template_item", this.templateItem)
                .putString("description", this.description)
                .putByte("decal", this.decal)
                .build();
    }
}
