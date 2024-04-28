package me.outspending.chunk;

import lombok.Getter;
import me.outspending.chunk.palette.ChunkPalette;

public record ChunkSection(ChunkPalette blockStates, ChunkPalette biomes) {
}
