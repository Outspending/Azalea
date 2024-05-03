package me.outspending.protocol;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Writable {
    void write(@NotNull PacketWriter writer);
}
