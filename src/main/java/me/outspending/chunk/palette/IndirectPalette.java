package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class IndirectPalette extends AbstractPalette {
    public IndirectPalette(byte bitsPerEntry) {
        super(bitsPerEntry);
    }

    @Override
    public void write(@NotNull PacketWriter writer, @NotNull Consumer<Palette> paletteConsumer) {
        // Bits Per Entry
        writer.writeByte(bitsPerEntry);

        // Palette
        writer.writeVarInt(1);
        writer.writeVarInt(1);

        // Data Array
        paletteConsumer.accept(this);
    }
}
