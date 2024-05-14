package me.outspending.chunk.palette;

import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public non-sealed class DirectPalette extends Palette {
    private short count;

    public DirectPalette(byte bitsPerEntry) {
        super(bitsPerEntry);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

        // Write Block Count
        writer.writeShort(this.count);

        // Write Palette

    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        return null;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {

    }
}
