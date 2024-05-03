package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class ChunkSection implements Writable {
    private static final int SECTION_SIZE = 16 * 16 * 16;
    private static final int GLOBAL_PALETTE_BIT_SIZE = 8; // Indirect

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

        this.data = new long[SECTION_SIZE];
        for (int i = 0; i < SECTION_SIZE; i++) {
            if (palette != null) {
                data[i] = palette.indexOf(types[i]);
            } else {
                data[i] = types[i];
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
        if (this.isEmpty()) {
            return;
        }

        writer.writeVarInt(GLOBAL_PALETTE_BIT_SIZE);
        if (palette != null) {
            writer.writeVarInt(palette.size());
            IntListIterator iterator = palette.iterator();
            while (iterator.hasNext()) {
                writer.writeVarInt(iterator.nextInt());
            }
        }

        writer.writeVarInt(data.length);
        for (long datum : data) {
            writer.writeVarLong(datum);
        }
    }
}
