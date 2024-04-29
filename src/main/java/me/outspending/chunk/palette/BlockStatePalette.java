package me.outspending.chunk.palette;

import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class BlockStatePalette implements ChunkPalette {
    public int SIZE = 16;

    private final ChunkPalette palette;
    private final byte bitsPerEntry;

    public BlockStatePalette(byte bitsPerEntry) {
        this.palette = getPalette(bitsPerEntry);
        this.bitsPerEntry = bitsPerEntry;
    }

    @Override
    public int getStateID(@NotNull BlockState data) {
        return palette.getStateID(data);
    }

    @Override
    public BlockState getStateForID(int id) {
        return palette.getStateForID(id);
    }

    @Override
    public byte getBitsPerBlock() {
        return palette.getBitsPerBlock();
    }

    @Override
    public void read(@NotNull PacketReader reader) {
        palette.read(reader);
    }

    @Override
    public void write(@NotNull PacketWriter writer, int size) {
        int dataLength = (SIZE * SIZE * SIZE) * bitsPerEntry / 64;
        long[] data = new long[dataLength];

        writer.writeByte(bitsPerEntry);
        palette.write(writer, dataLength);

        int valueMask = ((1 << bitsPerEntry) - 1);

        for (int y = 0; y < SIZE; y++) {
            for (int z = 0; z < SIZE; z++) {
                for (int x = 0; x < SIZE; x++) {
                    int blockNumber = (((y * SIZE) + z) * SIZE) + x;
                    int startLong = (blockNumber * bitsPerEntry) / 64;
                    int startOffset = (blockNumber * bitsPerEntry) % 64;
                    int endLong = ((blockNumber + 1) * bitsPerEntry - 1) / 64;

                    long value = 0;
                    value &= valueMask;

                    data[startLong] |= (value << startOffset);
                    if (startLong != endLong) {
                        data[endLong] |= (value >> (64 - startOffset));
                    }
                }
            }
        }

        writer.writeVarInt(dataLength);
        writer.writeLongArray(data);
    }
}
