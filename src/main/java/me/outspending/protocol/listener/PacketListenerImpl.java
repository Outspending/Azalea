package me.outspending.protocol.listener;

import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public non-sealed class PacketListenerImpl<T extends Packet> implements PacketListener<T> {
    private final Map<Class<T>, List<Consumer<T>>> listeners = new HashMap<>();

    PacketListenerImpl() {}

    @Override
    @SuppressWarnings("unchecked")
    public <P extends T> void addListener(@NotNull Class<P> clazz, @NotNull Consumer<@NotNull P> listener) {
        listeners.computeIfAbsent((Class<T>) clazz, k -> new ArrayList<>()).add((Consumer<T>) listener);
    }

    @Override
    public void addNode(@NotNull PacketNode<T> node) {
        Map<Class<T>, List<Consumer<T>>> listeners = node.getListeners();
        for (Map.Entry<Class<T>, List<Consumer<T>>> entry : listeners.entrySet()) {
            this.listeners.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPacketReceived(@NotNull T packet) {
        Class<T> packetClass = (Class<T>) packet.getClass();
        if (listeners.containsKey(packetClass))
            listeners.get(packetClass).forEach(listener -> listener.accept(packet));
    }

}
