package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public non-sealed class IndirectPalette extends Palette {
    private short count;
    private IntList palette;

    public IndirectPalette(@Range(from = 4, to = 8) byte bitsPerEntry) {
        super(bitsPerEntry);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

        // Block Count
        writer.writeShort(this.count);

        // Block Palette
        writer.writeByte(bitsPerEntry);
        if (palette != null) {
            writer.writeVarInt(palette.size());
            IntListIterator iterator = palette.iterator();
            while (iterator.hasNext()) {
                writer.writeVarInt(iterator.nextInt());
            }
        }

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
        BlockType blockType = BlockType.get((int) this.data[getBlockIndex(x, y, z)]);
        return blockType != null ? blockType : BlockType.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        int blockID = blockType.getId();
        if (!palette.contains(blockID)) {
            palette.add(blockID);
        }

        int index = getBlockIndex(x, y, z);
        long l = this.data[index / BLOCKS_PER_LONG];
        long mask = (1 << GLOBAL_PALETTE_BIT_SIZE) - 1;
        int offset = GLOBAL_PALETTE_BIT_SIZE * (index % BLOCKS_PER_LONG);
        l &= ~(mask << offset);
        l |= ((long) blockID << offset);

        if (blockID == 0) this.count--;
        else this.count++;

        this.data[index / BLOCKS_PER_LONG] = l;
    }

}
