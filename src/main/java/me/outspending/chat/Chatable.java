package me.outspending.chat;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface Chatable {

    void chat(@NotNull String message);

    void chat(@NotNull Component message);

    void sendMessage(@NotNull String message);

    void sendMessage(@NotNull Component message);

    void sendMessages(@NotNull String... messages);

    void sendMessages(@NotNull Component... messages);

}
