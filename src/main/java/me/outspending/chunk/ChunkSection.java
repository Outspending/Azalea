package me.outspending.chunk;

import me.outspending.chunk.palette.BiomesPalette;
import me.outspending.chunk.palette.BlockStatePalette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ChunkSection(BlockStatePalette blockStatesPalette, BiomesPalette biomesPalette) {

<<<<<<< Updated upstream
    public static ChunkSection[] createSections() {
        ChunkSection[] sections = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            sections[i] = new ChunkSection(new BlockStatePalette((byte) 15), new BiomesPalette((byte) 2));
=======
    private int count;
    private IntList palette;
    private long[] data;

    private void loadArray(int[] types) {
        try {
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

            this.data = new long[SECTION_SIZE / BLOCKS_PER_LONG];
            for (int i = 0; i < SECTION_SIZE; i++) {
                int index = i / BLOCKS_PER_LONG;
                int bitOffset = i % BLOCKS_PER_LONG;

                if (palette != null) {
                    data[index] |= (long) palette.indexOf(types[i]) << bitOffset;
                } else {
                    data[index] |= (long) types[i] << bitOffset;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> Stashed changes
        }

        return sections;
    }

<<<<<<< Updated upstream
=======
    public ChunkSection(int[] types) {
        loadArray(types);
    }

    private int getBlockIndex(int x, int y, int z) {
        return (y * 16 * 16) + (z * 16) + x;
    }

    public void setBlock(int x, int y, int z, int blockID) {
        if (!palette.contains(blockID)) {
            palette.add(blockID);
        }

        int index = getBlockIndex(x, y, z);
        long l = this.data[index / BLOCKS_PER_LONG];
        long mask = (1 << GLOBAL_PALETTE_BIT_SIZE) - 1;
        int offset = GLOBAL_PALETTE_BIT_SIZE * (index % BLOCKS_PER_LONG);
        l &= ~(mask << offset);
        l |= ((long) blockID << offset);

        this.data[index / BLOCKS_PER_LONG] = l;
    }

    public long getBlock(int x, int y, int z) {
        return this.data[getBlockIndex(x, y, z)];
    }

    public void fill(int blockID) {
        if (!palette.contains(blockID)) return;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    setBlock(x, y, z, blockID);
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
>>>>>>> Stashed changes
    public void write(@NotNull PacketWriter writer) {
        writer.writeShort((short) 0);
        blockStatesPalette.write(writer);
        biomesPalette.write(writer);
    }

}
