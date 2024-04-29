package me.outspending.entity;

import me.outspending.chunk.Chunk;

import java.util.List;

public interface Entity {
    List<Chunk> getChunksInDistance(int distance);
    List<Chunk> getLoadedChunks();
}
