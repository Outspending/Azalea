package me.outspending.chat.serializer;

import net.kyori.option.Option;

/**
 * Options that can apply to {@linkplain NBTComponentSerializer NBT serializers}.
 *
 * <p>See serializer documentation for specific details on which flags are supported.</p>
 *
 * @since 4.18.0
 */
public final class NBTSerializerOptions {
    /**
     * Whether to emit text components with no style and no children as plain text.
     *
     * @since 4.18.0
     * @sinceMinecraft 1.20.3
     */
    public static final Option<Boolean> EMIT_COMPACT_TEXT_COMPONENT = Option.booleanOption(key("emit/compact_text_component"), true);
    /**
     * Whether to serialize the types of {@linkplain net.kyori.adventure.text.Component components}.
     *
     * @since 4.18.0
     */
    public static final Option<Boolean> SERIALIZE_COMPONENT_TYPES = Option.booleanOption(key("serialize/component-types"), true);

    private NBTSerializerOptions() {
    }

    private static String key(final String value) {
        return "adventure:nbt/" + value;
    }
}