package me.outspending.registry.banner;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record BannerPattern(
        @NotNull NamespacedID registryID,
        @NotNull String assetID,
        @NotNull String translationKey
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .putString("asset_id", this.assetID)
                .putString("translation_key", this.translationKey)
                .build();
    }
}
