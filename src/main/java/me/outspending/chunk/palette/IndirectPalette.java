package me.outspending.chunk.palette;

import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class IndirectPalette implements ChunkPalette {
    private final byte bitsPerEntry;
    private final long[][][] dataArray;

    private final byte size;
    private final int dataLength;

    public IndirectPalette(byte bitsPerEntry, byte size) {
        this.bitsPerEntry = bitsPerEntry;
        this.dataArray = new long[size][size][size];
        this.size = size;
        this.dataLength = size * size * size;
    }

    @Override
    public int getStateID(@NotNull BlockState data) {
        return 0;
    }

    @Override
    public BlockState getStateForID(int id) {
        return null;
    }

    @Override
    public byte getBitsPerBlock() {
        return 0;
    }

    @Override
    public void read(@NotNull PacketReader reader) {

    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(dataLength);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    writer.writeLong(dataArray[x][y][z]);
                }
            }
        }
    }
}
