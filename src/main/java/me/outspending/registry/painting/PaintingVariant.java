package me.outspending.registry.painting;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record PaintingVariant(
        @NotNull NamespacedID registryID,
        @NotNull String assetID,
        byte width,
        byte height
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .putString("asset_id", this.assetID)
                .putByte("width", this.width)
                .putByte("height", this.height)
                .build();
    }
}
