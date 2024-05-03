package me.outspending.chunk;

import lombok.Getter;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Chunk implements Writable {

    private final int chunkX;
    private final int chunkZ;
    private final ChunkSection[] chunkSections;

    private ChunkSection[] generateSections() {
        ChunkSection[] generated = new ChunkSection[24];
        for (int i = 0; i < 24; i++) {
            generated[i] = new ChunkSection(new int[]{0, 1});
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
