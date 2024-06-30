package me.outspending.protocol.listener;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import me.outspending.connection.ClientConnection;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PacketListenerImpl<T extends Packet> implements PacketListener<T> {
    private final Multimap<Class<T>, BiConsumer<ClientConnection, T>> listeners = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);

    @Override
    @SuppressWarnings("unchecked")
    public <P extends T> void addListener(@NotNull Class<P> clazz, @NotNull BiConsumer<@NotNull ClientConnection, @NotNull P> listener) {
        listeners.put((Class<T>) clazz, (BiConsumer<ClientConnection, T>) listener);
    }

    @Override
    public void addNode(@NotNull PacketNode<T> node) {
        final Multimap<Class<T>, BiConsumer<ClientConnection, T>> listeners = node.getListeners();
        for (Map.Entry<Class<T>, BiConsumer<ClientConnection, T>> entry : listeners.entries()) {
            this.listeners.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPacketReceived(@NotNull ClientConnection connection, @NotNull T packet) {
        final Class<T> packetClass = (Class<T>) packet.getClass();
        if (listeners.containsKey(packetClass))
            listeners.get(packetClass).forEach(listener -> listener.accept(connection, packet));
    }

}
