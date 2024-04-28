package me.outspending.chunk.palette;

import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public interface ChunkPalette {
    static ChunkPalette choosePalette(byte bitsPerBlock) {
        if (bitsPerBlock <= 4) {
            return new IndirectPalette((byte) 4);
        } else if (bitsPerBlock <= 8) {
            return new IndirectPalette(bitsPerBlock);
        } else {
            return new DirectPalette();
        }
    }

    int getStateID(@NotNull BlockState data);
    BlockState getStateForID(int id);
    byte getBitsPerBlock();

    void read(@NotNull PacketReader reader);
    void write(@NotNull PacketWriter writer);
}
