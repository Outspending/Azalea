package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public sealed abstract class AbstractPalette implements Palette permits BiomesPalette, BlockStatePalette {

    protected final byte bitsPerEntry;
    protected final byte size;
    protected final long[] values;

    protected int count;
    protected Int2IntOpenHashMap blocks;

    public AbstractPalette(byte bitsPerEntry, byte size) {
        this.count = (int) ((Math.pow(size, 3) * bitsPerEntry) / 64);
        this.values = new long[count];
        this.bitsPerEntry = bitsPerEntry;
        this.size = size;
        this.blocks = new Int2IntOpenHashMap(size);
    }

    @Override
    public int get(int x, int y, int z) {
        return blocks.get(getCoordIndex(x, y, z));
    }

    @Override
    public void set(int x, int y, int z, int value) {
        blocks.put(getCoordIndex(x, y, z), value);
    }

    @Override
    public int bitsPerEntry() {
        return bitsPerEntry;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public long[] data() {
        return values;
    }

    @Override
    public Int2IntOpenHashMap blocks() {
        return blocks;
    }

    public int getCoordIndex(int x, int y, int z) {
        return (y * 16 + z) * 16 + x;
    }

    public int getSectionIndex(int y) {
        return y >> 4;
    }
}
