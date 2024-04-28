package me.outspending.chunk.palette;

import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class DirectPalette  implements ChunkPalette {
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
        // TODO: Ceil(Log2(BlockState.TotalNumberOfStates))
        return (byte) Math.ceil(Math.log(2) / Math.log(2));
    }

    @Override
    public void read(@NotNull PacketReader reader) {
        // No Data
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        // No Data
    }
}
