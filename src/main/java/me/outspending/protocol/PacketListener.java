package me.outspending.protocol;

import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PacketListener<T extends Packet> {
    void onPacketReceive(@NotNull T packet);
}
