package me.outspending.protocol;

import org.jetbrains.annotations.NotNull;

public interface Packet {
    void write(@NotNull PacketWriter writer);
    int getID();
}
