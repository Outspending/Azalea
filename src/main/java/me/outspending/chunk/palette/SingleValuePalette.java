package me.outspending.chunk.palette;

import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class SingleValuePalette implements ChunkPalette {
    private final int blockID;

    public SingleValuePalette(int blockID) {
        this.blockID = blockID;
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
        // No Data
    }

    @Override
    public void write(@NotNull PacketWriter writer, int size) {
        writer.writeVarInt(blockID);
    }
}
