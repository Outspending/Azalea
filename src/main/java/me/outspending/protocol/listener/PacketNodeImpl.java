package me.outspending.protocol.listener;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.NetworkClient;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public non-sealed class PacketNodeImpl<T extends Packet> implements PacketNode<T> {
    private final Multimap<Class<T>, BiConsumer<ClientConnection, T>> listeners = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    PacketNodeImpl() {}

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <P extends T> PacketNode<T> addListener(@NotNull Class<P> packetType, @NotNull BiConsumer<@NotNull ClientConnection, @NotNull P> listener) {
        listeners.put((Class<T>) packetType, (BiConsumer<ClientConnection, T>) listener);
        return this;
    }

    @Override
    public @NotNull Multimap<Class<T>, BiConsumer<ClientConnection, T>> getListeners() {
        return listeners;
    }

}
