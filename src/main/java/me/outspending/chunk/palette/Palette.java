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
    protected static final byte GLOBAL_PALETTE_BIT_SIZE = 8;
    protected static final int BLOCKS_PER_LONG = 64 / GLOBAL_PALETTE_BIT_SIZE;

    protected final byte bitsPerEntry;
    protected long[] data;

    protected void compressDataArray(int[] types, IntList palette) {
        this.data = new long[SECTION_SIZE / BLOCKS_PER_LONG];
        for (int i = 0; i < SECTION_SIZE; i++) {
            int index = i / BLOCKS_PER_LONG;
            int bitOffset = i % BLOCKS_PER_LONG;

            if (palette != null) {
                data[index] |= (long) palette.indexOf(types[i]) << bitOffset;
            } else {
                data[index] |= (long) types[i] << bitOffset;
            }
        }
    }

    public Palette(byte bitsPerEntry) {
        this.bitsPerEntry = bitsPerEntry;
    }

    public int getBlockIndex(int x, int y, int z) {
        return (y * 16 * 16) + (z * 16) + x;
    }

    public abstract void write(@NotNull PacketWriter writer);

}
