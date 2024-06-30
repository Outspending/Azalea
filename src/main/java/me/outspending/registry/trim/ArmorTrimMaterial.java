package me.outspending.registry.trim;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ArmorTrimMaterial(
        @NotNull NamespacedID registryID,
        @NotNull String assetName,
        @NotNull String ingredient,
        float item_model_index,
        @NotNull String description
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .putString("asset_name", this.assetName)
                .putString("ingredient", this.ingredient)
                .putFloat("item_model_index", this.item_model_index)
                .putString("description", this.description)
                .build();
    }
}
