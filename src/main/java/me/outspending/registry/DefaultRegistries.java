package me.outspending.registry;

import me.outspending.registry.chat.ChatType;
import me.outspending.registry.chat.ChatTypes;
import me.outspending.registry.dimension.Dimension;
import me.outspending.registry.dimension.DimensionType;

import java.util.Arrays;

public class DefaultRegistries {
    public static final Registry<Dimension> DIMENSION;
    public static final Registry<ChatType> CHATTYPES;

    private static <T> Registry<T> register(Registry<T> registry, T... values) {
        registry.addAll(values);
        return registry;
    }

    private static <T> Registry<T> registerNew(T... values) {
        DefaultedRegistry<T> registry = new DefaultedRegistry<>();
        register(registry, values);
        return registry;
    }

    static {
        DIMENSION = registerNew(DimensionType.values());
        CHATTYPES = registerNew(ChatTypes.allDefault());
    }

}
