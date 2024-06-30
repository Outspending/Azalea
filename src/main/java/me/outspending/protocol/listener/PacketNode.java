package me.outspending.protocol.listener;

import com.google.common.collect.Multimap;
import me.outspending.connection.ClientConnection;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public sealed interface PacketNode<T extends Packet> permits PacketNodeImpl {

    static <T extends Packet> @NotNull PacketNode<T> create() {
        return new PacketNodeImpl<>();
    }

    @Contract("_, _ -> this")
    <P extends T> @NotNull PacketNode<T> addListener(@NotNull Class<P> packetType, @NotNull BiConsumer<@NotNull ClientConnection, @NotNull P> listener);

    @NotNull Multimap<Class<T>, BiConsumer<ClientConnection, T>> getListeners();

}
