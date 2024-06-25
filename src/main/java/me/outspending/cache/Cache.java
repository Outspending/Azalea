package me.outspending.cache;

import org.jetbrains.annotations.*;

import java.util.Collection;

@ApiStatus.NonExtendable
public sealed interface Cache<K> permits PlayerCache, WorldCache {

    @Contract("null -> fail")
    void add(@UnknownNullability K key);

    @Contract("null -> fail")
    void remove(@UnknownNullability K key);

    @Contract("null -> null")
    @Nullable K get(@UnknownNullability String key);

    @NotNull Collection<K> getAll();

}
