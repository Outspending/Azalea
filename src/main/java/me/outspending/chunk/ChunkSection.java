package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.outspending.chunk.palette.Palette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class ChunkSection {
    private final Int2IntOpenHashMap blocks = new Int2IntOpenHashMap(4096);

    private final Palette blockPalette;
    private final Palette biomesPalette;

    public ChunkSection(Palette blockPalette, Palette biomesPalette) {
        this.blockPalette = blockPalette;
        this.biomesPalette = biomesPalette;
    }

    public void setBlock(int x, int y, int z, int blockID) {
        blocks.put(getCoordIndex(x, y, z), blockID);
    }

    public int getBlock(int x, int y, int z) {
        return blocks.get(getCoordIndex(x, y, z));
    }

    public void fill(int blockID) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    setBlock(x, y, z, blockID);
                }
            }
        }
    }

    private int getCoordIndex(int x, int y, int z) {
        return (y * 16 + z) * 16 + x;
    }

    public void write(@NotNull PacketWriter writer) {
        writer.writeShort((short) blocks.size());
        blockPalette.write(writer);
        biomesPalette.write(writer);
    }

}
