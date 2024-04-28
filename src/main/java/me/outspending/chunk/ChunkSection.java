package me.outspending.chunk;

import me.outspending.chunk.palette.BiomesPalette;
import me.outspending.chunk.palette.BlockStatePalette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ChunkSection(BlockStatePalette blockStates, BiomesPalette biomes) {

    public void write(@NotNull PacketWriter writer) {
        writer.writeShort((short) 0);
        blockStates.write(writer);
        biomes.write(writer);
    }

}
