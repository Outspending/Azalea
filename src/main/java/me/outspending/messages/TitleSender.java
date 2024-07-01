package me.outspending.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface TitleSender {

    void sendTitle(@NotNull Title title);

    void sendTitle(@NotNull Component component);

    default void sendTitle(@NotNull String string) {
        sendTitle(Component.text(string));
    }

    void sendSubtitle(@NotNull Component component);

    default void sendSubtitle(@NotNull String string) {
        sendSubtitle(Component.text(string));
    }

    void sendTitle(@NotNull Component title, @NotNull Component subtitle);

    default void sendTitle(@NotNull String title, @NotNull String subtitle) {
        sendTitle(Component.text(title), Component.text(subtitle));
    }

    default void sendTitle(@NotNull Component title, @NotNull Component subtitle, int fadeIn, int stay, int fadeOut) {
        this.sendTitle(Title.title(title, subtitle,
                Title.Times.times(
                        Duration.ofMillis(fadeIn * 50L),
                        Duration.ofMillis(stay * 50L),
                        Duration.ofMillis(fadeOut * 50L)
                ))
        );
    }

    default void sendTitle(@NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
        this.sendTitle(Component.text(title), Component.text(subtitle), fadeIn, stay, fadeOut);
    }

    void resetTitle();

}
