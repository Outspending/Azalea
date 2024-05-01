package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface Palette {
    int count();
    int bitsPerEntry();
    long[] data();

    void write(@NotNull PacketWriter writer);
}
