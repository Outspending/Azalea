package me.outspending.chunk;

import me.outspending.chunk.palette.BiomesPalette;
import me.outspending.chunk.palette.BlockStatePalette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ChunkSection(BlockStatePalette blockStatesPalette, BiomesPalette biomesPalette) {
    public void setBlock(int x, int y, int z, int blockID) {
        blockStatesPalette.set(x, y, z, blockID);
    }

    public int getBlock(int x, int y, int z) {
        return blockStatesPalette.get(x, y, z);
    }

    void write(@NotNull PacketWriter writer) {
        writer.writeShort((short) 0);
        blockStatesPalette.write(writer);
        biomesPalette.write(writer);
    }

}
