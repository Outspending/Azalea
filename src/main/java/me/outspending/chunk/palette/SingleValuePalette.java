package me.outspending.chunk.palette;

import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public non-sealed class SingleValuePalette extends Palette {
    private static final byte SINGLE_BITS = 0;

    private BlockType value;

    public SingleValuePalette(BlockType type) {
        super((byte) 0);
        this.value = type;

        Arrays.fill(data, type.getId());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

        // Write Block Count
        writer.writeShort(this.value == null ? (short) 0 : (short) 1);

        // Write Palette
        writer.writeByte(SINGLE_BITS);
        writer.writeVarInt(this.value.getId());

        writer.writeVarInt(data.length);
        for (long datum : data) {
            writer.writeLong(datum);
        }

        // Biome Palette
        writer.writeByte((byte) 0);
        writer.writeVarInt(0);
        writer.writeVarInt(0);

    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        return value != null ? value : BlockType.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        Arrays.fill(data, blockType.getId());
        this.value = blockType;
    }

}
