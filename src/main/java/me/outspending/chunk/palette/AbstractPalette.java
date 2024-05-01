package me.outspending.chunk.palette;

public sealed abstract class AbstractPalette implements Palette permits DirectPalette, IndirectPalette, SingleValuePalette {
    protected final byte bitsPerEntry;
    protected final long[] values;

    protected final int size;
    protected final int count;

    public AbstractPalette(byte bitsPerEntry, int size) {
        this.count = (int) ((Math.pow(size, 3) * bitsPerEntry) / 64);
        this.values = new long[count];
        this.bitsPerEntry = bitsPerEntry;
        this.size = size;
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
}
