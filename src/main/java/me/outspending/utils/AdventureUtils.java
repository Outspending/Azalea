package me.outspending.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class AdventureUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final JSONComponentSerializer jsonComponentSerializer = JSONComponentSerializer.json();


    public static String serializeJson(@NotNull Component component) {
        return jsonComponentSerializer.serialize(component);
    }

    public static Component serializeString(@NotNull String string) {
        return miniMessage.deserialize(string);
    }
}
