package me.outspending.chunk;

import lombok.Getter;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@Getter
public class Chunk implements Writable {

    private final int chunkX;
    private final int chunkZ;
    private final ChunkSection[] chunkSections;

    private int[] initializeArray() {
        int[] array = new int[4096];
        Arrays.fill(array, 1);
        return array;
    }

    private ChunkSection[] generateSections() {
        ChunkSection[] generated = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            generated[i] = new ChunkSection(initializeArray());
        }
        return generated;
    }

    public Chunk(int chunkX, int chunkZ) {
        this.chunkSections = generateSections();
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        for (ChunkSection section : chunkSections) {
            section.write(writer);
        }
    }
}
