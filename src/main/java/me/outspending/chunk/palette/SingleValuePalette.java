package me.outspending.chunk.palette;

import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public non-sealed class SingleValuePalette extends Palette {
    private int value;

    public SingleValuePalette(BlockType type) {
        super((byte) 0);
        this.value = type.getId();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(this.value);
    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        BlockType type = BlockType.get(value);
        return type != null ? type : BlockType.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        this.value = blockType.getId();
    }

}
