package me.outspending.chunk;

import me.outspending.block.BlockType;
import me.outspending.chunk.palette.DirectPalette;
import me.outspending.chunk.palette.IndirectPalette;
import me.outspending.chunk.palette.Palette;
import me.outspending.chunk.palette.SingleValuePalette;
import me.outspending.generation.BlockGetter;
import me.outspending.generation.BlockSetter;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class ChunkSection implements Writable, BlockGetter, BlockSetter {
    private static final byte CHUNK_SECTION_SIZE = 24;
    private static final int SECTION_SIZE = 16 * 16 * 16;

    private final Palette chunkPalette;

    public static ChunkSection[] generateChunkSections() {
        ChunkSection[] sections = new ChunkSection[CHUNK_SECTION_SIZE];
        for (int i = 0; i < CHUNK_SECTION_SIZE; i++) {
            sections[i] = new ChunkSection((byte) 15);
        }

        return sections;
    }

    private Palette getPalette(byte bitsPerEntry) {
        if (bitsPerEntry == 0) {
            return new SingleValuePalette(BlockType.AIR);
        } else if (bitsPerEntry >= 4 && bitsPerEntry <= 8) {
            return new IndirectPalette(bitsPerEntry, new int[SECTION_SIZE]);
        } else if (bitsPerEntry >= 15) {
            return new DirectPalette(bitsPerEntry, new int[SECTION_SIZE]);
        } else {
            throw new IllegalArgumentException("Unknown bitsPerEntry Palette Type!");
        }
    }

    public ChunkSection(byte bitsPerEntry) {
        this.chunkPalette = getPalette(bitsPerEntry);
    }

    @Override
    public void setBlock(int x, int y, int z, @NotNull BlockType blockType) {
        chunkPalette.setBlock(x, y, z, blockType);
    }

    @Override
    public @NotNull BlockType getBlock(int x, int y, int z) {
        return chunkPalette.getBlock(x, y, z);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        chunkPalette.write(writer);
    }

}