package me.outspending.registry.damage;

import me.outspending.NamespacedID;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record DamageType(
        @NotNull NamespacedID registryID,
        @NotNull String messageID,
        @NotNull String scaling,
        float exhaustion,
        @NotNull Optional<String> effects,
        @NotNull Optional<String> death_message_type
) implements RegistryType {
    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder()
                .putString("message_id", this.messageID)
                .putString("scaling", this.scaling)
                .putFloat("exhaustion", this.exhaustion);

        this.effects.ifPresent(effects -> builder.putString("effects", effects));
        this.death_message_type.ifPresent(death_message_type -> builder.putString("death_message_type", death_message_type));

        return builder.build();
    }
}
