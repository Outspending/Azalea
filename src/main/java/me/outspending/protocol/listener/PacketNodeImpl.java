package me.outspending.protocol.listener;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public non-sealed class PacketNodeImpl<T extends Packet> implements PacketNode<T> {
    private final HashMap<Class<T>, List<Consumer<T>>> listeners = new HashMap<>();

    PacketNodeImpl() {}

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <E extends T> PacketNode<T> addListener(@NotNull Class<E> packetType, @NotNull Consumer<@NotNull E> listener) {
        listeners.computeIfAbsent((Class<T>) packetType, k -> new ArrayList<>()).add((Consumer<T>) listener);
        return this;
    }

    @Override
    public @NotNull Map<Class<T>, List<Consumer<T>>> getListeners() {
        return Collections.unmodifiableMap(listeners);
    }

    private record Handle() {}
}
