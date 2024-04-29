package me.outspending.chunk;

import me.outspending.chunk.palette.BiomesPalette;
import me.outspending.chunk.palette.BlockStatePalette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ChunkSection(BlockStatePalette blockStatesPalette, BiomesPalette biomesPalette) {

    public static ChunkSection[] createSections() {
        ChunkSection[] sections = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            sections[i] = new ChunkSection(new BlockStatePalette((byte) 15), new BiomesPalette((byte) 2));
        }

        return sections;
    }

    public void write(@NotNull PacketWriter writer) {
        writer.writeShort((short) 0);
        blockStatesPalette.write(writer);
        biomesPalette.write(writer);
    }

}
