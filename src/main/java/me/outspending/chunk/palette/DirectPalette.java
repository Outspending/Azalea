package me.outspending.chunk.palette;

import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public non-sealed class DirectPalette extends Palette {
    private short count;

    public DirectPalette(byte bitsPerEntry, int[] types) {
        super(bitsPerEntry);
        compressDataArray(types, null);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

        // Block Count
        writer.writeShort(this.count);

        // Block Palette
        writer.writeByte(bitsPerEntry);
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
        int index = getBlockIndex(x, y, z);
        int bitOffset = index % blocksPerLong;
        int compressedDataIndex = index / blocksPerLong;
        long blockData = this.data[compressedDataIndex];

        int blockType = (int) ((blockData >> bitOffset) & ((1 << bitsPerEntry) - 1));
        BlockType type = BlockType.get(blockType);

        return type != null ? type : BlockType.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        int blockID = blockType.getId();
        int index = getBlockIndex(x, y, z);

        long l = this.data[index / blocksPerLong];
        long mask = (1 << bitsPerEntry) - 1;
        int offset = bitsPerEntry * (index % blocksPerLong);
        l &= ~(mask << offset);
        l |= ((long) blockID << offset);

        if (blockID == 0) this.count--;
        else this.count++;

        this.data[index / blocksPerLong] = l;
    }
}
