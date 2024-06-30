package me.outspending.registry.wolf;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import me.outspending.registry.biome.Biome;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record WolfVariant(
        @NotNull NamespacedID registryID,
        @NotNull String wildTexture,
        @NotNull String tameTexture,
        @NotNull String angryTexture,
        @NotNull Biome spawnableBiome
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .putString("wild_texture", this.wildTexture)
                .putString("tame_texture", this.tameTexture)
                .putString("angry_texture", this.angryTexture)
                .putString("biomes", this.spawnableBiome.getRegistryID().getPath())
                .build();
    }
}
