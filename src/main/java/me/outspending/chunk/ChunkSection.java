package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

<<<<<<< HEAD
public class ChunkSection implements Writable {
    private static final int SECTION_SIZE = 16 * 16 * 16;
    private static final int GLOBAL_PALETTE_BIT_SIZE = 8; // Indirect
=======
import java.util.function.Consumer;

public class ChunkSection {
    private final Int2IntOpenHashMap blocks = new Int2IntOpenHashMap();
>>>>>>> d1297cbc1e6a7b24c078895117bac97b585c7cc2

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
<<<<<<< HEAD
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
=======
        writer.writeShort((short) blocks.size());
        blockPalette.write(writer, palette -> {
            int dataLength = (16*16*16) * palette.bitsPerEntry() / 64;
            long[] data = new long[dataLength];

            int individualValueMask = (1 << palette.bitsPerEntry()) - 1;

            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        int blockNumber = (((y * 16) + z) * 16) + x;
                        int startLong = (blockNumber * palette.bitsPerEntry()) / 64;
                        int startBit = (blockNumber * palette.bitsPerEntry()) % 64;
                        int endLong = ((blockNumber + 1) * palette.bitsPerEntry() - 1) / 64;

                        long value = blocks.get(getCoordIndex(x, y, z));
                        value &= individualValueMask;

                        data[startLong] |= value << startBit;
                        if (startLong != endLong) {
                            data[endLong] = (value >> (64 - startBit));
                        }
                    }
                }
            }

            writer.writeVarInt(dataLength);
            writer.writeLongArray(data);
        });

        biomesPalette.write(writer, palette ->  {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    writer.writeInt(127);
                }
            }
        });
>>>>>>> d1297cbc1e6a7b24c078895117bac97b585c7cc2
    }
}
