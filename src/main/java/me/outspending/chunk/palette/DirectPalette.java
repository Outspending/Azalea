package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public final class DirectPalette extends AbstractPalette {
    public DirectPalette(byte bitsPerEntry, byte size) {
        super(bitsPerEntry, size);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {

    }
}
