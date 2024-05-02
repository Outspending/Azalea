package me.outspending.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.outspending.chunk.palette.Palette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ChunkSection {
    private final Int2IntOpenHashMap blocks = new Int2IntOpenHashMap();

    private final Palette blockPalette;
    private final Palette biomesPalette;

    public ChunkSection(Palette blockPalette, Palette biomesPalette) {
        this.blockPalette = blockPalette;
        this.biomesPalette = biomesPalette;
    }

    public void setBlock(int x, int y, int z, int blockID) {
        blocks.put(getCoordIndex(x, y, z), blockID);
    }

    public int getBlock(int x, int y, int z) {
        return blocks.get(getCoordIndex(x, y, z));
    }

    public void fill(int blockID) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    setBlock(x, y, z, blockID);
                }
            }
        }
    }

    private int getCoordIndex(int x, int y, int z) {
        return (y * 16 + z) * 16 + x;
    }

    public void write(@NotNull PacketWriter writer) {
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
    }
}
