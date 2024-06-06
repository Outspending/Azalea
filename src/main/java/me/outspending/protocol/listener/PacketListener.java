package me.outspending.protocol.listener;

import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface PacketListener<T extends Packet> {

    static <T extends Packet> @NotNull PacketListener<T> create(Class<T> packetType) {
        return new PacketListenerImpl<>();
    }

    <P extends T> void addListener(@NotNull Class<P> clazz, @NotNull Consumer<@NotNull P> listener);

    void addNode(@NotNull PacketNode<T> node);

    void onPacketReceived(@NotNull T packet);

}
