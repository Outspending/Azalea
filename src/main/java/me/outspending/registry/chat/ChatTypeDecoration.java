package me.outspending.registry.chat;

import me.outspending.NamespacedID;
import net.kyori.adventure.nbt.BinaryTagTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ChatTypeDecoration(
        @NotNull NamespacedID translationKey,
        @NotNull List<Parameter> parameters,
        @Nullable Style style
) {
    public CompoundBinaryTag toNBT() {
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder()
                .putString("translation_key", this.translationKey.toString());

        ListBinaryTag.Builder<StringBinaryTag> binaryParameters = ListBinaryTag.builder(BinaryTagTypes.STRING);
        this.parameters.forEach(parameter -> binaryParameters.add(StringBinaryTag.stringBinaryTag(parameter.toLowerCase())));

        builder.put("parameters", binaryParameters.build());

        return builder.build();
    }

    public enum Parameter {
        SENDER,
        TARGET,
        CONTENT;

        public String toLowerCase() {
            return this.name().toLowerCase();
        }
    }
}
