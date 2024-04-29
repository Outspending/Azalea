package me.outspending.chunk.palette;

import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class IndirectPalette implements ChunkPalette {
    private final byte bitsPerBlock;

    public IndirectPalette(byte bitsPerBlock) {
        this.bitsPerBlock = bitsPerBlock;
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
        return bitsPerBlock;
    }

    @Override
    public void read(@NotNull PacketReader reader) {

    }

    @Override
    public void write(@NotNull PacketWriter writer, int size) {
        writer.writeVarInt(size);
        for (int i = 0; i < size; i++) {
            writer.writeVarInt(1);
        }
    }
}
