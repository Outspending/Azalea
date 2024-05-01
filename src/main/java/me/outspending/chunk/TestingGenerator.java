package me.outspending.chunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class TestingGenerator implements ChunkGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TestingGenerator.class);

    private int counter = 0;

    @Override
    public void onChunkLoad(CompletableFuture<Chunk> chunk) {
        chunk.thenAccept(chunk1 -> {
            ChunkSection section = chunk1.getSection(40);

            logger.info("Loading Chunk " + counter++ + ": " + section);

            section.fill(1);
        });
    }

    @Override
    public void onChunkUnload(CompletableFuture<Chunk> chunk) {

    }
}
