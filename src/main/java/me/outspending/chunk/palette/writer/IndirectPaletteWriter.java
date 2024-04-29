package me.outspending.chunk.palette.writer;

import com.google.common.base.Preconditions;
import me.outspending.chunk.palette.Palette;
import me.outspending.protocol.writer.PacketWriter;

public non-sealed class IndirectPaletteWriter implements PaletteWriter {
    @Override
    public void write(PacketWriter writer, Palette palette) {
        Preconditions.checkNotNull(writer, "Palette Writer cannot be null!");
        Preconditions.checkNotNull(palette, "Palette cannot be null!");

        writer.writeVarInt(palette.count());
        for (int id : palette.blocks().values()) {
            writer.writeVarInt(id);
        }
    }
}
