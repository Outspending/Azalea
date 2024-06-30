package me.outspending.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

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

    default void sendTitleAndSubtitle(@NotNull Component title, @NotNull Component subtitle) {
        sendTitle(title);
        sendSubtitle(subtitle);
    }

    default void sendTitleAndSubtitle(@NotNull String title, @NotNull String subtitle) {
        sendTitleAndSubtitle(Component.text(title), Component.text(subtitle));
    }

}
