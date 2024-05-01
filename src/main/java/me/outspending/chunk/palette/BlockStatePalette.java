package me.outspending.chunk.palette;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.outspending.chunk.palette.writer.IndirectPaletteWriter;
import me.outspending.chunk.palette.writer.PaletteWriter;
import me.outspending.protocol.writer.PacketWriter;

import java.io.IOException;

@Getter
public non-sealed class BlockStatePalette extends AbstractPalette {
    private final PaletteWriter paletteWriter;

    public BlockStatePalette(byte bitsPerEntry) {
        super(bitsPerEntry, (byte) 4096);
        this.paletteWriter = new IndirectPaletteWriter();
    }

    @Override
    public void write(PacketWriter writer) {
        Preconditions.checkNotNull(writer, "Palette Writer cannot be null!");

        writer.writeByte(bitsPerEntry);
        paletteWriter.write(writer, this);

        int valueMask = ((1 << bitsPerEntry) - 1);
        int blockNumber = 0;
        int startLong = 0;

        for (int y = 0; y < size; y++) {
            for (int z = 0; z < size; z++) {
                for (int x = 0; x < size; x++) {
                    int startOffset = (blockNumber * bitsPerEntry) % 64;
                    int endLong = ((blockNumber + 1) * bitsPerEntry - 1) / 64;

                    long value = 1;

                    values[startLong] |= (value << startOffset);
                    if (startLong != endLong) {
                        values[endLong] |= (value >> (64 - startOffset));
                    }

                    blockNumber++;
                    if (blockNumber % 64 == 0) {
                        startLong++;
                    }
                }
            }
        }

        writer.writeVarInt(count());
        writer.writeLongArray(data());
    }

}
