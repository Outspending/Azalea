package me.outspending.chunk.palette.writer;

import me.outspending.chunk.palette.Palette;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

public sealed interface PaletteWriter permits DirectPaletteWriter, IndirectPaletteWriter {
    @Contract("null, _ -> fail; _, null -> fail")
    void write(PacketWriter writer, Palette palette);
}
