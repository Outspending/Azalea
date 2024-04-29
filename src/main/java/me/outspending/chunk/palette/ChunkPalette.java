package me.outspending.chunk.palette;

import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public interface ChunkPalette {
    default ChunkPalette getPalette(byte bitsPerEntry) {
        if (bitsPerEntry == 0) {
            return new SingleValuePalette(0);
        } else if (bitsPerEntry <= 4) {
            return new IndirectPalette(bitsPerEntry);
        } else {
            return new DirectPalette();
        }
    }

    int getStateID(@NotNull BlockState data);
    BlockState getStateForID(int id);
    byte getBitsPerBlock();

    void read(@NotNull PacketReader reader);
    void write(@NotNull PacketWriter writer, int size);
}
