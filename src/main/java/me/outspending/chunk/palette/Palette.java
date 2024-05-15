package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import me.outspending.block.BlockType;
import me.outspending.generation.BlockGetter;
import me.outspending.generation.BlockSetter;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public sealed abstract class Palette implements BlockGetter, BlockSetter permits DirectPalette, IndirectPalette, SingleValuePalette {
    protected static final int CHUNK_SECTION_SIZE = 24;
    protected static final int SECTION_SIZE = 16 * 16 * 16;

    protected final byte bitsPerEntry;
    protected final int blocksPerLong;
    protected long[] data;

    protected void compressDataArray(int[] types, IntList palette) {
        this.data = new long[SECTION_SIZE / blocksPerLong];
        for (int i = 0; i < SECTION_SIZE; i++) {
            int index = i / blocksPerLong;
            int bitOffset = i % blocksPerLong;

            if (palette != null) {
                // Indirect Palette
                data[index] |= (long) palette.indexOf(types[i]) << bitOffset;
            } else {
                // Direct Palette
                data[index] |= (long) types[i] << bitOffset;
            }
        }
    }

    public Palette(byte bitsPerEntry) {
        this.bitsPerEntry = bitsPerEntry;
        this.blocksPerLong = 64 / bitsPerEntry;
    }

    public int getBlockIndex(int x, int y, int z) {
        return (y * 16 * 16) + (z * 16) + x;
    }

    public abstract void write(@NotNull PacketWriter writer);

}
