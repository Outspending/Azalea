package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import me.outspending.block.BlockType;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public non-sealed class IndirectPalette extends Palette {
    private short count;
    private IntList palette;

    private void handleList(int[] types) {
        this.count = 0;
        this.palette = new IntArrayList();

        for (int type : types) {
            if (type != 0) {
                count++;
            }

            if (!palette.contains(type)) {
                palette.add(type);
            }
        }

        compressDataArray(types, palette);
    }

    public IndirectPalette(@Range(from = 4, to = 8) byte bitsPerEntry, int[] types) {
        super(bitsPerEntry);
        handleList(types);
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
        int index = getBlockIndex(x, y, z);
        int bitOffset = index % blocksPerLong;
        int compressedDataIndex = index / blocksPerLong;
        long blockData = this.data[compressedDataIndex];

        int paletteIndex = (int) ((blockData >> bitOffset) & ((1 << bitsPerEntry) - 1));
        int blockType = palette.getInt(paletteIndex);

        BlockType type = BlockType.get(blockType);
        return type != null ? type : BlockType.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        int blockID = blockType.getId();
        if (!palette.contains(blockID)) {
            palette.add(blockID);
        }

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
