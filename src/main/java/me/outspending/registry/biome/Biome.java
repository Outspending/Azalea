package me.outspending.registry.biome;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record Biome(
        @NotNull NamespacedID registryID,
        byte hasPrecipitation,
        float temperature,
        @NotNull Optional<String> temperatureModifier,
        float downfall,
        @NotNull BiomeEffects effects
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder()
                .putByte("has_precipitation", this.hasPrecipitation)
                .putFloat("temperature", this.temperature)
                .putFloat("downfall", this.downfall)
                .put("effects", this.effects.toNBT());

        this.temperatureModifier.ifPresent(temperatureModifier -> builder.putString("temperature_modifier", temperatureModifier));

        return builder.build();
    }
}
