package me.outspending.messages;

import me.outspending.player.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Chatable {

    default void chat(@NotNull String message) {
        chat(Component.text(message));
    }

    void chat(@NotNull Component message);

    default void sendMessage(@NotNull String message) {
        sendMessage(Component.text(message));
    }

    void sendMessage(@NotNull Component message);

}
