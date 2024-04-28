package me.outspending.chunk.palette;

import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class BiomesPalette implements ChunkPalette {
    private final IndirectPalette palette;

    public BiomesPalette(byte bitsPerEntry) {
        this.palette = new IndirectPalette(bitsPerEntry, (byte) 4);
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
    public void write(@NotNull PacketWriter writer) {
        palette.write(writer);
    }
}
