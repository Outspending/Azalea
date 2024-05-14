package me.outspending.protocol.listener;

import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public sealed interface PacketNode<T extends Packet> permits PacketNodeImpl {

    static <T extends Packet> @NotNull PacketNode<T> create(Class<T> type) {
        return new PacketNodeImpl<>();
    }

    @Contract("_, _ -> this")
    @NotNull <E extends T> PacketNode<T> addListener(@NotNull Class<E> packetType, @NotNull Consumer<@NotNull E> listener);

    @NotNull
    Map<Class<T>, List<Consumer<T>>> getListeners();

}
