package me.outspending.registry.biome;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record BiomeEffects(
        int fogColor,
        int waterColor,
        int waterFogColor,
        int skyColor,
        @NotNull Optional<Integer> foliageColor,
        @NotNull Optional<Integer> grassColor,
        @NotNull Optional<String> grassColorModifier
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return NamespacedID.EMPTY;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder()
                .putInt("fog_color", this.fogColor)
                .putInt("water_color", this.waterColor)
                .putInt("water_fog_color", this.waterFogColor)
                .putInt("sky_color", this.skyColor);

        this.foliageColor.ifPresent(foliageColor -> builder.putInt("foliage_color", foliageColor));
        this.grassColor.ifPresent(grassColor -> builder.putInt("grass_color", grassColor));
        this.grassColorModifier.ifPresent(grassColorModifier -> builder.putString("grass_color_modifier", grassColorModifier));

        return builder.build();
    }
}
