package me.outspending.registry.chat;

import lombok.Getter;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ChatTypeDecoration(
        @NotNull String translationKey,
        @NotNull List<Parameter> parameters,
        @NotNull Style style
) {
    public enum Parameter {
        SENDER,
        TARGET,
        CONTENT
    }
}
