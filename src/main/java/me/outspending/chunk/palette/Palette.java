package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

public interface Palette {
    static Palette blockPalette() {
        return new BlockStatePalette((byte) 15);
    }

    static Palette getPalette(byte bitsPerEntry) {
        return new BlockStatePalette(bitsPerEntry);
    }

    int get(int x, int y, int z);
    void set(int x, int y, int z, int value);
    int count();
    int bitsPerEntry();
    long[] data();
    Int2IntOpenHashMap blocks();

    @Contract("null -> fail")
    void write(PacketWriter writer);
}
