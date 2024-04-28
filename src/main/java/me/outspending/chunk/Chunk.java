package me.outspending.chunk;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class Chunk {
    public static void writeEmptyLightMaskTo(@NotNull PacketWriter writer, @NotNull LightType type) {
        BitSet bs = new BitSet();
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 25) {
                continue;
            }

            bs.set(i, true);
        }

        writer.writeVarInt(bs.length());
        if (!bs.isEmpty())
            writer.writeLongArray(bs.toLongArray());
    }
}
