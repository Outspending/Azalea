package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class ChunkSection implements Writable {
    private static final int SECTION_SIZE = 16 * 16 * 16;
    private static final byte GLOBAL_PALETTE_BIT_SIZE = 8; // Indirect

    private int count;
    private IntList palette;
    private long[] data;

    private void loadArray(int[] types) {
        this.count = 0;
        this.palette = new IntArrayList();
        for (int type : types) {
            if (type != 0) {
                count++;
            }

            if (!palette.contains(type)) {
                palette.add(type);
            }
        }

        this.data = new long[SECTION_SIZE / GLOBAL_PALETTE_BIT_SIZE];
        for (int i = 0; i < SECTION_SIZE; i++) {
            if (palette != null) {
                data[i] |= palette.indexOf(types[i]) << (i * GLOBAL_PALETTE_BIT_SIZE);
            } else {
                data[i] |= types[i] << (i * GLOBAL_PALETTE_BIT_SIZE);
            }
        }
    }

    public ChunkSection(int[] types) {
        loadArray(types);
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        // Block Count
        writer.writeShort((short) 0);

        // Block Palette
        writer.writeByte(GLOBAL_PALETTE_BIT_SIZE);
        if (palette != null) {
            writer.writeVarInt(palette.size());
            IntListIterator iterator = palette.iterator();
            while (iterator.hasNext()) {
                writer.writeVarInt(iterator.nextInt());
            }
        }

        writer.writeVarInt(data.length);
        for (long datum : data) {
            writer.writeLong(datum);
        }

        // Biome Palette
        writer.writeByte((byte) 0);
        writer.writeVarInt(0);

        writer.writeVarInt(0);
    }
}
