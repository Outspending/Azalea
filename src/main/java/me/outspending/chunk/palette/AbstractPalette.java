package me.outspending.chunk.palette;

public sealed abstract class AbstractPalette implements Palette permits DirectPalette, IndirectPalette, SingleValuePalette {
    protected final byte bitsPerEntry;

    public AbstractPalette(byte bitsPerEntry) {
        this.bitsPerEntry = bitsPerEntry;
    }

    @Override
    public int bitsPerEntry() {
        return bitsPerEntry;
    }
}
