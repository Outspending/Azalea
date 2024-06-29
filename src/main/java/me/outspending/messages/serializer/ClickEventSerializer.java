package me.outspending.messages.serializer;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;

final class ClickEventSerializer {

    private static final String ACTION = "action";
    private static final String VALUE = "value";

    private ClickEventSerializer() {
    }

    static @NotNull ClickEvent deserialize(@NotNull CompoundBinaryTag tag) {
        BinaryTag actionTag = tag.get(ACTION);

        if (actionTag == null) {
            throw new IllegalArgumentException("The serialized click event doesn't contain an action");
        }

        String actionString = ((StringBinaryTag) actionTag).value();
        ClickEvent.Action action = ClickEvent.Action.NAMES.valueOrThrow(actionString);

        return ClickEvent.clickEvent(action, tag.getString(VALUE));
    }

    static @NotNull CompoundBinaryTag serialize(@NotNull ClickEvent event) {
        return CompoundBinaryTag.builder()
                .putString(ACTION, ClickEvent.Action.NAMES.keyOrThrow(event.action()))
                .putString(VALUE, event.value())
                .build();
    }
}