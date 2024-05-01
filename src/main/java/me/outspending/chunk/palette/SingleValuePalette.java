package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public final class SingleValuePalette extends AbstractPalette {
    private final int blockID;

    public SingleValuePalette(byte size, int blockID) {
        super((byte) 0, size);

        this.blockID = blockID;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(blockID);
    }
}
