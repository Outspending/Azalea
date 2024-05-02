package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class DirectPalette extends AbstractPalette {
    public DirectPalette(byte bitsPerEntry) {
        super(bitsPerEntry);
    }

    @Override
    public void write(@NotNull PacketWriter writer, @NotNull Consumer<Palette> paletteConsumer) {}
}
