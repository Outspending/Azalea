package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public final class IndirectPalette extends AbstractPalette {
    public IndirectPalette(byte bitsPerEntry, int size) {
        super(bitsPerEntry, size);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        // TODO: This is temporary, this contains the registry ID's
        writer.writeVarInt(1);
        writer.writeVarInt(1);
    }
}
