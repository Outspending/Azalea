package me.outspending.chunk.palette;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class SingleValuePalette extends AbstractPalette {
    private final int blockID;

    public SingleValuePalette(int blockID) {
        super((byte) 0);

        this.blockID = blockID;
    }

    @Override
    public void write(@NotNull PacketWriter writer, @NotNull Consumer<Palette> paletteConsumer) {
        writer.writeVarInt(blockID);
    }
}
