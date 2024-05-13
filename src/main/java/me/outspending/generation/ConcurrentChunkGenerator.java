package me.outspending.generation;

import me.outspending.block.BlockType;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.CompletableFuture;

public class ConcurrentChunkGenerator extends AbstractChunkGenerator {

    public ConcurrentChunkGenerator(Chunk chunk) {
        super(chunk);
    }

    @Override
    public void fill(@NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fill(blockType));
    }

    @Override
    public void fillSection(@Range(from = 0, to = 24) int sectionY, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillSection(sectionY, blockType));
    }

    @Override
    public void fillSection(ChunkSection section, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillSection(section, blockType));
    }

    @Override
    public void fillHeight(int height, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillHeight(height, blockType));
    }

    @Override
    public void fillHeight(int minHeight, int maxHeight, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillHeight(minHeight, maxHeight, blockType));
    }

    @Override
    public void fillY(ChunkSection section, int y, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillY(section, y, blockType));
    }

    @Override
    public void fillY(int y, @NotNull BlockType blockType) {
        CompletableFuture.runAsync(() -> super.fillY(y, blockType));
    }

}
